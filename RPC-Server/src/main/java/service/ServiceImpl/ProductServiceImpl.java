package service.ServiceImpl;

import dto.Product;
import service.ProductService;

public class ProductServiceImpl implements ProductService {

    @Override
    public Product searchProduct(Integer id) {
        System.out.println("[Server] User query the Product");
        return Product.builder().productName("GG Milk").productId(1).merchantId(1).build();
    }

    @Override
    public Integer createProduct(Product product) {
        return 1;
    }
}