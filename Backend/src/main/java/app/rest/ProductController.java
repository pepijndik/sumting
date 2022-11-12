package app.rest;

import app.models.Product.Product;
import app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer productId) {
        return new ResponseEntity<>(productRepository.findById(productId), HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        Product productToDelete = productRepository.findById(productId);
        productRepository.delete(productToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/findByProjectId")
    public ResponseEntity<Iterable<Product>> getProductsByProjectId(@RequestParam(value = "id") Integer id) {
        return new ResponseEntity<>(productRepository.findByProjectId(id), HttpStatus.OK);
    }
}
