package app;

import app.models.Product.Product;
import app.models.Project.Project;
import app.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ProductRepository productRepository() {
            return new ProductRepository() {
                // implement methods

            };
        }
    }

    private Product GenerateProduct(Integer id, String name){
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(100.00);
        product.setProject(new Project());
        product.setCreatedAt(LocalDateTime.now());
        product.setDescription("A very cool test product");

        return product;
    }

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void should_find_a_product_by_id() {
        Product product = GenerateProduct(1, "Cool testing name");
        this.productRepository.save(product);
        Assertions.assertEquals(this.productRepository.findById(product.getId()).getId(), product.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void should_find_none_if_repository_is_empty() {
        Iterable<Product> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void should_store_a_product() {
        Product product = GenerateProduct(1, "Cool testing name");
        Product savedProduct = this.productRepository.save(product);
        assertThat(savedProduct).hasFieldOrPropertyWithValue("price", 100.00);
        assertThat(savedProduct).hasFieldOrPropertyWithValue("name", "Cool testing name");
    }
}
