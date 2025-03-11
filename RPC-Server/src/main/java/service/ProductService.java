package service;


import dto.Product;

public interface ProductService {
    Product searchProduct(Integer id);
    Integer createProduct(Product product);
}
