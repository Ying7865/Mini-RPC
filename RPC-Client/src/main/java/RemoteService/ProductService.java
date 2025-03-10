package RemoteService;

import DTO.Product;

public interface ProductService {
    Product searchProduct(Integer id);
    Integer createProduct(Product product);
}
