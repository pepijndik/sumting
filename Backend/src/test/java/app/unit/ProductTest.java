package app.unit;

import app.models.Product.Product;
import app.repositories.DataLoader;
import app.repositories.ProductRepository;
import app.rest.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the ProductRepository
 * @author Colin Laan
 * @version 1.0
 * @since 1.0
 * @see ProductRepository
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Import(DataLoader.class)
public class ProductTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    CommandLineRunner dataLoader;
    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> products;

    /**
     * Sets up the test
     * @throws Exception if something goes wrong
     * @see ProductRepository
     * @see DataLoader
     * @see Product
     * @author Colin Laan
     */
    @BeforeEach
    void setup() throws Exception {
        this.dataLoader.run(null);
        Product p = new Product();
        p.setName("test");
        p.setActive(true);
        p.setCreatedAt(LocalDateTime.now());
        p.setDescription("test123");
        p.setPrice(1.0);

        Product p2 = new Product();
        p2.setName("test2");
        p2.setActive(true);
        p2.setCreatedAt(LocalDateTime.now());
        p2.setDescription("test321");
        p2.setPrice(1.0);
        this.productRepository.save(p);
        this.productRepository.save(p2);

        this.products = (List<Product>) this.productRepository.findAll();
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
    }

    /**
     * Tests if the repository returns all products
     * @see ProductController:findAll()
     * @author Colin Laan
     */
    @Test
    @org.junit.jupiter.api.Order(1)
    void testGetAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(servletContextPath + "/products", Product[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products.size(), response.getBody().length);
    }

    /**
     * Tests if the controller returns a product by id
     * @see ProductController:findById(int)
     * @author Colin Laan
     */
    @Test
    @org.junit.jupiter.api.Order(2)
    void testGetProductById() {
        ResponseEntity<Product> response = restTemplate.getForEntity(servletContextPath + "/products/" + products.get(0).getId(), Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products.get(0).getId(), response.getBody().getId());
    }

    /**
     * Tests if the controller doesn't find something when given an invalid id
     * @see ProductController:findById(int)
     * @author Colin Laan
     */
    @Test
    @org.junit.jupiter.api.Order(3)
    void testGetProductByIdNotFound() {
        ResponseEntity<Product> response = restTemplate.getForEntity(servletContextPath + "/products/" + 999999, Product.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests if the controller is able to delete a product
     * @see ProductController:deleteProduct(int)
     * @author Colin Laan
     */
    @Test
    @org.junit.jupiter.api.Order(4)
    void testDeleteProductById() {
        restTemplate.delete(servletContextPath + "/products/" + products.get(0).getId());
        ResponseEntity<Product> response = restTemplate.getForEntity(servletContextPath + "/products/" + products.get(0).getId(), Product.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
