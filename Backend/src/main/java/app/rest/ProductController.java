package app.rest;

import app.models.Product.Product;
import app.repositories.ProductRepository;
import app.views.ProductView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Product controller to handle product endpoints
 * @author Pepijn dik
 */
@Controller
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get all products
     * @return ResponseEntity<>
     */
    @GetMapping("/products")
    @JsonView(ProductView.Overview.class)
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Get a product by id
     * @param productId Product id
     * @return ResponseEntity<>
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Integer productId) {
        Product product = productRepository.findById(productId);
        if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Delete a product by id
     * @param productId product id
     * @return ResponseEntity<>
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        Product productToDelete = productRepository.findById(productId);
        productRepository.delete(productToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find a product by project id
     * @param id project id
     * @return ResponseEntity<>
     */
    @GetMapping("/products/findByProjectId")
    public ResponseEntity<Iterable<Product>> getProductsByProjectId(@RequestParam(value = "id") Integer id) {
        return new ResponseEntity<>(productRepository.findByProjectId(id), HttpStatus.OK);
    }
}
