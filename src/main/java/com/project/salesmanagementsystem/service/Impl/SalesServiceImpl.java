package com.project.salesmanagementsystem.service.Impl;
import com.project.salesmanagementsystem.DTO.*;
import com.project.salesmanagementsystem.error.InvalidInputException;
import com.project.salesmanagementsystem.error.ObjectNotFoundException;
import com.project.salesmanagementsystem.model.Product;
import com.project.salesmanagementsystem.model.Sales;
import com.project.salesmanagementsystem.repository.SalesRepository;
import com.project.salesmanagementsystem.service.ClientService;
import com.project.salesmanagementsystem.service.ProductService;
import com.project.salesmanagementsystem.service.SalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import static com.project.salesmanagementsystem.mapper.ClientMapper.mapToClient;
import static com.project.salesmanagementsystem.mapper.ProductMapper.mapToProduct;
import static com.project.salesmanagementsystem.mapper.SalesMapper.mapToSalesDto;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;
    private final ProductService productService;
    private final ClientService clientService;
    @Override
    public List<SalesDTO> findAllSales() {
        return salesRepository.findAll() != null ? salesRepository.findAll().stream()
                .map(sales -> mapToSalesDto(sales)).collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    @Transactional
    public SalesDTO saveSales(SalesDTO salesDTO) {
        if(salesDTO.getId() != null){
            throw new InvalidInputException("Invalid use of save method, Try update?");
        }
        if(salesDTO.getClient() == null || salesDTO.getClient().getId() == null){
            throw new InvalidInputException("You can't add a Sale without a client! Please provide the client ID.");
        }
        if(salesDTO.getProducts() == null){
            throw new InvalidInputException("You can't create a sale without any products!");
        }
        Sales sales = new Sales();
        for(ProductDTO productDTO:salesDTO.getProducts()){
            if(productDTO.getId() != null) {
                // reduce number of quantity avail each time.
                ProductDTO productDTO1 = productService.findProductById(productDTO.getId());
                if(productDTO1.getAvailableQuantity() != 0) {
                    productDTO1.setAvailableQuantity(productDTO1.getAvailableQuantity() - 1);
                    productService.updateProduct(productDTO1);
                    sales.addToProducts(mapToProduct(productDTO1));
                }else{
                    throw new InvalidInputException("You can't create a sale with product quantity = 0 !");
                }
            }else{
                throw new InvalidInputException("You can't create a sale with a product id=null !");
            }
        }
//        if(sales.getProducts() == null){
//            throw new InvalidInputException("You can't create a sale without any valid products!");
//        }
        sales.setClient(mapToClient(clientService.findClientById(salesDTO.getClient().getId())));
        sales.setTotal(sales.getTotal());
        salesRepository.save(sales);
        return mapToSalesDto(sales);
    }
    @Override
    public SalesDTO updateSales(SalesDTO salesDTO) {
        if(salesDTO.getId() == null){
            throw new InvalidInputException("Invalid use of update method, Try save?");
        }
        Optional<Sales> salesOptional = salesRepository.findById(salesDTO.getId());
        if(salesOptional.isEmpty()){
            throw new ObjectNotFoundException("No sales record with id=%s was found!".formatted(salesDTO.getId()));
        }
        Set<Product> products = new HashSet<>();
        for(ProductDTO productDTO:salesDTO.getProducts()){
            if(productDTO.getId() != null)
                products.add(mapToProduct(productService.findProductById(productDTO.getId())));
        }
        Sales sales = salesOptional.get();
        sales.setProducts(products);
        sales.setClient(mapToClient(clientService.findClientById(salesDTO.getClient().getId())));
        sales.setTotal(sales.getTotal());
        salesRepository.save(sales);
        return mapToSalesDto(sales);

    }

    @Override
    public void deleteSalesById(long id) {
        salesRepository.deleteById(id);
    }
    // ************************************************************
    // Sales report
    @Override
    public SalesReportDTO getSalesReport(Timestamp start, Timestamp end) {
        SalesReportDTO salesReportDTO = SalesReportDTO.builder().build();
        List<Sales> salesList = salesRepository.findTopPerformingSellers(start, end);
        List<Long> productList = salesRepository.findTopProductsBetweenTwoDates(start, end);
        List<ProductDTO> topProducts = new ArrayList<>();
        for(long i:productList){
            topProducts.add(productService.findProductById(i));
        }
        double revenue = 0;
        for(Sales sales : salesList)
            revenue += sales.getTotal();
        salesReportDTO.setTotalRevenue(revenue);
        salesReportDTO.setTotalSales(salesList.size());
        salesReportDTO.setTopSellingProducts(topProducts);
        salesReportDTO.setTopPerformingSellers(salesList.stream().map(sales -> mapToSalesDto(sales)).collect(Collectors.toList()));
        return salesReportDTO;
    }
    // Client report
    @Override
    public ClientReportDTO getClientReport() {
        ClientDTO client = clientService.findClientById(salesRepository.findTopClient());
        List<ClientDTO> clientDTOS = clientService.findAllClients(); // total number of clients
        List<LocationStatDTO> locationStatDTOS = clientService.findLocationStats();
        ClientReportDTO clientReportDTO = ClientReportDTO.builder()
                .totalClients(clientDTOS.size())
                .topClient(client)
                .locationStatDTOS(locationStatDTOS)
                .build();
        return clientReportDTO;
    }


}
