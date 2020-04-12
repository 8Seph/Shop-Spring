package ru.geekbrains.supershop.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.supershop.exceptions.EntityNotFoundException;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Review;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.supershop.persistence.pojo.ProductPojo;
import ru.geekbrains.supershop.persistence.pojo.ReviewPojo;
import ru.geekbrains.supershop.persistence.repositories.ShopuserRepository;
import ru.geekbrains.supershop.services.ImageService;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Создать интеграционные тесты для методов
 * Optional<List<Review>> getReviewsByProduct,
 * Optional<List<Review>> getReviewsByShopuser,
 * и UUID moderate для сервиса ReviewService;
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopuserService shopuserService;

    @Autowired
    private ImageService imageService;

    private Product product;
    private Review testReview;
    private Shopuser shopuser;

    @Before
    public void setUp() {
        shopuser = shopuserService.getAnonymousUser(); //6b718067-e1e4-4202-a7e2-7339ea0d6cb4

        ProductPojo productPojo = ProductPojo.builder()
                .title("testProduct")
                .available(true)
                .category(ProductCategory.DRINK)
                .description("none")
                .price(20.00)
                .build();
        productService.save(productPojo, null);

        product = productService.findProductByTitle(productPojo.getTitle());

        testReview = Review.builder()
                .shopuser(shopuser)
                .product(product)
                .commentary("testcomment")
                .approved(true)
                .build();
        reviewService.save(testReview);
    }

    @Test
    public void mustWillReturnReviewsByProduct() throws Exception {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(testReview);
        assertEquals(reviews, reviewService.getReviewsByProduct(product).orElse(new ArrayList<>()));
    }

    @Test
    public void mustWillReturnReviewsByUser() throws Exception {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(testReview);
        assertEquals(reviews, reviewService.getReviewsByShopuser(shopuser).orElse(new ArrayList<>()));
    }

    @Test
    public void testUUID() throws Exception {
        assertEquals(product.getId(), reviewService.moderate(testReview.getId(), "approve"));
    }

    @After
    public void clear() {
        reviewService.removeReviewById(testReview.getId());
        productService.removeProductById(product.getId());
    }
}
