package Service;


import DTO.Product;

public interface ProductService {
    Product searchProduct(int id);
    Integer createProduct(Product product);
}
