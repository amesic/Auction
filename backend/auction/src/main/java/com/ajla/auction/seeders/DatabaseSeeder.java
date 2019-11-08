package com.ajla.auction.seeders;

import com.ajla.auction.model.Category;
import com.ajla.auction.model.Image;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.User;
import com.ajla.auction.repo.CategoryRepository;

import com.ajla.auction.repo.ImageRepository;
import com.ajla.auction.repo.ProductRepository;
import com.ajla.auction.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Component
public class DatabaseSeeder {
    //properties
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final ImageRepository imageProductRepo;
    Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    //dependency injection
    @Autowired
    public DatabaseSeeder(final CategoryRepository categoryRepo, final ProductRepository productRepo, final UserRepository userRepo, final ImageRepository imageProductRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.imageProductRepo = imageProductRepo;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUser();
        seedCategories();
        seedProducts();
        seedImagesForProducts();
    }
    private void seedUser() {
        User user = userRepo.findUserById((long) 1);
        if(user == null) {
            final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User u = new User();
            u.setUserName("Huso Husic");
            u.setPassword(passwordEncoder.encode("12345678"));
            u.setEmail("huso@gmail.com");

            User u1 = new User();
            u1.setUserName("Mujo Mujic");
            u1.setPassword(passwordEncoder.encode("12345678"));
            u1.setEmail("mujo@gmail.com");

            User u2 = new User();
            u2.setUserName("Fata Fatic");
            u2.setPassword(passwordEncoder.encode("12345678"));
            u2.setEmail("fata@gmail.com");

            User u3 = new User();
            u3.setUserName("Suljo Suljic");
            u3.setPassword(passwordEncoder.encode("12345678"));
            u3.setEmail("suljo@gmail.com");

            userRepo.saveAll(Arrays.asList(u, u1, u2, u3));
            logger.info("User table seeded");
        }
        else {
            logger.trace("User Seeding Not Required");
        }

    }
    private void seedCategories() {
        final Category oneOfParentsCategories = categoryRepo.findCategoryById((long) 1);
        if (oneOfParentsCategories == null) {
            Category c1 = new Category();
            c1.setName("Fashion");
            Category c2 = new Category();
            c2.setName("Women's Fashion");
            Category c3 = new Category();
            c3.setName("Men's Fashion");
            c1.setSubcategories(Arrays.asList(c2, c3));

            Category c4 = new Category();
            c4.setName("Accessories");
            Category c5 = new Category();
            c5.setName("Bags");
            Category c6 = new Category();
            c6.setName("Sunglasses");
            c4.setSubcategories(Arrays.asList(c5, c6));

            Category c7 = new Category();
            c7.setName("Jewelry");
            Category c8 = new Category();
            c8.setName("Necklaces");
            Category c9 = new Category();
            c9.setName("Watches");
            c7.setSubcategories(Arrays.asList(c8, c9));

            Category c10 = new Category();
            c10.setName("Shoes");
            Category c11 = new Category();
            c11.setName("Casual Shoes");
            Category c12 = new Category();
            c12.setName("Athletic Shoes");
            c10.setSubcategories(Arrays.asList(c11, c12));

            Category c13 = new Category();
            c13.setName("Sportware");
            Category c14 = new Category();
            c14.setName("Fitness");
            Category c15 = new Category();
            c15.setName("Winter Sports");
            c13.setSubcategories(Arrays.asList(c14, c15));

            Category c16 = new Category();
            c16.setName("Home");
            Category c17 = new Category();
            c17.setName("Home Decor");
            c16.setSubcategories(Arrays.asList(c17));

            Category c18 = new Category();
            c18.setName("Electronics");
            Category c19 = new Category();
            c19.setName("Tablet");
            Category c20 = new Category();
            c20.setName("Laptop");
            c18.setSubcategories(Arrays.asList(c19, c20));

            Category c21 = new Category();
            c21.setName("Mobile");
            Category c22 = new Category();
            c22.setName("Cell Phone Accessories");
            Category c23 = new Category();
            c23.setName("Cell Phone Chargers");
            c21.setSubcategories(Arrays.asList(c22, c23));

            Category c24 = new Category();
            c24.setName("Computers");
            Category c25 = new Category();
            c25.setName("Computer components");
            c24.setSubcategories(Arrays.asList(c25));

            Category c26 = new Category();
            c26.setName("Music");
            Category c27 = new Category();
            c27.setName("Records");
            Category c28 = new Category();
            c28.setName("CDs");
            c26.setSubcategories(Arrays.asList(c27, c28));

            Category c29 = new Category();
            c29.setName("Art");
            Category c30 = new Category();
            c30.setName("Art Posters");
            Category c31 = new Category();
            c31.setName("Paintings");
            c29.setSubcategories(Arrays.asList(c30, c31));

            categoryRepo.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7,
                    c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20,
                    c21, c22, c23, c24, c25, c26, c27, c28, c29, c30, c31));
            logger.info("Category table seeded");
        }
        else {
            logger.trace("Category Seeding Not Required");
        }
    }
    private void seedProducts() {
        final Product oneOfProducts = productRepo.findProductById((long) 1);
        if (oneOfProducts == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            Product p = new Product();
            p.setTitle("Women Winter Jacket");
            Category c = categoryRepo.findCategoryById((long) 1);
            p.setCategory(c);
            c = categoryRepo.findCategoryById((long) 2);
            p.setSubcategory(c);
            p.setDatePublishing((LocalDate.parse("2.11.2019", formatter)));
            p.setStartDate((LocalDate.parse("2.11.2019", formatter)));
            p.setEndDate((LocalDate.parse("7.11.2019", formatter)));
            User u = userRepo.findUserById((long) 1);
            p.setSeller(u);
            p.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p.setFeature(false);
            p.setStartPrice(90);

            Product p1 = new Product();
            p1.setTitle("Men Winter Jacket");
            c = categoryRepo.findCategoryById((long) 1);
            p1.setCategory(c);
            c = categoryRepo.findCategoryById((long) 3);
            p1.setSubcategory(c);
            p1.setDatePublishing((LocalDate.parse("2.11.2019", formatter)));
            p1.setStartDate((LocalDate.parse("6.11.2019", formatter)));
            p1.setEndDate((LocalDate.parse("6.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p1.setSeller(u);
            p1.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p1.setFeature(true);
            p1.setStartPrice(1000);

            Product p2 = new Product();
            p2.setTitle("Women Big Frame Sunglasses");
            c = categoryRepo.findCategoryById((long) 4);
            p2.setCategory(c);
            c = categoryRepo.findCategoryById((long) 6);
            p2.setSubcategory(c);
            p2.setDatePublishing((LocalDate.parse("5.11.2019", formatter)));
            p2.setStartDate((LocalDate.parse("1.11.2019", formatter)));
            p2.setEndDate((LocalDate.parse("7.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p2.setSeller(u);
            p2.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p2.setFeature(false);
            p2.setStartPrice(500);

            Product p3 = new Product();
            p3.setTitle("Chanel Leather Handbag");
            c = categoryRepo.findCategoryById((long) 4);
            p3.setCategory(c);
            c = categoryRepo.findCategoryById((long) 5);
            p3.setSubcategory(c);
            p3.setDatePublishing((LocalDate.parse("3.11.2019", formatter)));
            p3.setStartDate((LocalDate.parse("1.10.2019", formatter)));
            p3.setEndDate((LocalDate.parse("6.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p3.setSeller(u);
            p3.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p3.setFeature(true);
            p3.setStartPrice(1500);

            Product p4 = new Product();
            p4.setTitle("Women Diamond Necklace");
            c = categoryRepo.findCategoryById((long) 7);
            p4.setCategory(c);
            c = categoryRepo.findCategoryById((long) 8);
            p4.setSubcategory(c);
            p4.setDatePublishing((LocalDate.parse("5.11.2019", formatter)));
            p4.setStartDate((LocalDate.parse("25.09.2019", formatter)));
            p4.setEndDate((LocalDate.parse("25.01.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p4.setSeller(u);
            p4.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p4.setFeature(true);
            p4.setStartPrice(1000);

            Product p5 = new Product();
            p5.setTitle("Men Sports Watch");
            c = categoryRepo.findCategoryById((long) 7);
            p5.setCategory(c);
            c = categoryRepo.findCategoryById((long) 9);
            p5.setSubcategory(c);
            p5.setDatePublishing((LocalDate.parse("5.11.2019", formatter)));
            p5.setStartDate((LocalDate.parse("25.03.2019", formatter)));
            p5.setEndDate((LocalDate.parse("25.01.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p5.setSeller(u);
            p5.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p5.setFeature(true);
            p5.setStartPrice(30);

            Product p6 = new Product();
            p6.setTitle("Nike White Casual Shoes");
            c = categoryRepo.findCategoryById((long) 10);
            p6.setCategory(c);
            c = categoryRepo.findCategoryById((long) 11);
            p6.setSubcategory(c);
            p6.setDatePublishing((LocalDate.parse("10.11.2019", formatter)));
            p6.setStartDate((LocalDate.parse("1.01.2020", formatter)));
            p6.setEndDate((LocalDate.parse("25.01.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p6.setSeller(u);
            p6.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p6.setFeature(false);
            p6.setStartPrice(100);

            Product p7 = new Product();
            p7.setTitle("Women Running Athletic Shoes");
            c = categoryRepo.findCategoryById((long) 10);
            p7.setCategory(c);
            c = categoryRepo.findCategoryById((long) 12);
            p7.setSubcategory(c);
            p7.setDatePublishing((LocalDate.parse("2.11.2019", formatter)));
            p7.setStartDate((LocalDate.parse("6.11.2019", formatter)));
            p7.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p7.setSeller(u);
            p7.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p7.setFeature(true);
            p7.setStartPrice(100);

            Product p8 = new Product();
            p8.setTitle("Women Yoga Set");
            c = categoryRepo.findCategoryById((long) 13);
            p8.setCategory(c);
            c = categoryRepo.findCategoryById((long) 14);
            p8.setSubcategory(c);
            p8.setDatePublishing((LocalDate.parse("2.11.2019", formatter)));
            p8.setStartDate((LocalDate.parse("3.11.2019", formatter)));
            p8.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p8.setSeller(u);
            p8.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p8.setFeature(true);
            p8.setStartPrice(200);

            Product p9 = new Product();
            p9.setTitle("Men Winter Skiing Clothes Coat");
            c = categoryRepo.findCategoryById((long) 13);
            p9.setCategory(c);
            c = categoryRepo.findCategoryById((long) 15);
            p9.setSubcategory(c);
            p9.setDatePublishing((LocalDate.parse("27.11.2019", formatter)));
            p9.setStartDate((LocalDate.parse("4.08.2019", formatter)));
            p9.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p9.setSeller(u);
            p9.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p9.setFeature(false);
            p9.setStartPrice(200);

            Product p10 = new Product();
            p10.setTitle("Wall Stickers");
            c = categoryRepo.findCategoryById((long) 16);
            p10.setCategory(c);
            c = categoryRepo.findCategoryById((long) 17);
            p10.setSubcategory(c);
            p10.setDatePublishing((LocalDate.parse("27.11.2019", formatter)));
            p10.setStartDate((LocalDate.parse("1.03.2019", formatter)));
            p10.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p10.setSeller(u);
            p10.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p10.setFeature(true);
            p10.setStartPrice(5);

            Product p11 = new Product();
            p11.setTitle("Apple iPad");
            c = categoryRepo.findCategoryById((long) 18);
            p11.setCategory(c);
            c = categoryRepo.findCategoryById((long) 19);
            p11.setSubcategory(c);
            p11.setDatePublishing((LocalDate.parse("30.11.2019", formatter)));
            p11.setStartDate((LocalDate.parse("31.02.2019", formatter)));
            p11.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p11.setSeller(u);
            p11.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p11.setFeature(false);
            p11.setStartPrice(500);

            Product p12 = new Product();
            p12.setTitle("Apple MacBook");
            c = categoryRepo.findCategoryById((long) 18);
            p12.setCategory(c);
            c = categoryRepo.findCategoryById((long) 20);
            p12.setSubcategory(c);
            p12.setDatePublishing((LocalDate.parse("30.11.2019", formatter)));
            p12.setStartDate((LocalDate.parse("31.12.2019", formatter)));
            p12.setEndDate((LocalDate.parse("25.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p12.setSeller(u);
            p12.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p12.setFeature(false);
            p12.setStartPrice(700);

            Product p13 = new Product();
            p13.setTitle("IPhone 8 Phone Case");
            c = categoryRepo.findCategoryById((long) 21);
            p13.setCategory(c);
            c = categoryRepo.findCategoryById((long) 22);
            p13.setSubcategory(c);
            p13.setDatePublishing((LocalDate.parse("01.12.2019", formatter)));
            p13.setStartDate((LocalDate.parse("1.12.2019", formatter)));
            p13.setEndDate((LocalDate.parse("4.02.2020", formatter)));
            u = userRepo.findUserById((long) 1);
            p13.setSeller(u);
            p13.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p13.setFeature(true);
            p13.setStartPrice(10);

            Product p14 = new Product();
            p14.setTitle("IPhone 8 Charger");
            c = categoryRepo.findCategoryById((long) 21);
            p14.setCategory(c);
            c = categoryRepo.findCategoryById((long) 23);
            p14.setSubcategory(c);
            p14.setDatePublishing((LocalDate.parse("01.12.2019", formatter)));
            p14.setStartDate((LocalDate.parse("1.10.2019", formatter)));
            p14.setEndDate((LocalDate.parse("8.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p14.setSeller(u);
            p14.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p14.setFeature(false);
            p14.setStartPrice(30);

            Product p15 = new Product();
            p15.setTitle("Computer Mouse");
            c = categoryRepo.findCategoryById((long) 24);
            p15.setCategory(c);
            c = categoryRepo.findCategoryById((long) 25);
            p15.setSubcategory(c);
            p15.setDatePublishing((LocalDate.parse("13.12.2019", formatter)));
            p15.setStartDate((LocalDate.parse("13.10.2019", formatter)));
            p15.setEndDate((LocalDate.parse("9.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p15.setSeller(u);
            p15.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p15.setFeature(false);
            p15.setStartPrice(10);

            Product p16 = new Product();
            p16.setTitle("Bijelo Dugme Records");
            c = categoryRepo.findCategoryById((long) 26);
            p16.setCategory(c);
            c = categoryRepo.findCategoryById((long) 27);
            p16.setSubcategory(c);
            p16.setDatePublishing((LocalDate.parse("18.12.2019", formatter)));
            p16.setStartDate((LocalDate.parse("20.10.2019", formatter)));
            p16.setEndDate((LocalDate.parse("8.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p16.setSeller(u);
            p16.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p16.setFeature(false);
            p16.setStartPrice(50);

            Product p17 = new Product();
            p17.setTitle("Weeknd CDs");
            c = categoryRepo.findCategoryById((long) 26);
            p17.setCategory(c);
            c = categoryRepo.findCategoryById((long) 28);
            p17.setSubcategory(c);
            p17.setDatePublishing((LocalDate.parse("15.11.2019", formatter)));
            p17.setStartDate((LocalDate.parse("1.11.2019", formatter)));
            p17.setEndDate((LocalDate.parse("4.12.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p17.setSeller(u);
            p17.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p17.setFeature(true);
            p17.setStartPrice(20);

            Product p18 = new Product();
            p18.setTitle("Joker Poster");
            c = categoryRepo.findCategoryById((long) 29);
            p18.setCategory(c);
            c = categoryRepo.findCategoryById((long) 30);
            p18.setSubcategory(c);
            p18.setDatePublishing((LocalDate.parse("15.11.2019", formatter)));
            p18.setStartDate((LocalDate.parse("1.11.2019", formatter)));
            p18.setEndDate((LocalDate.parse("9.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p18.setSeller(u);
            p18.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p18.setFeature(true);
            p18.setStartPrice(35);

            Product p19 = new Product();
            p19.setTitle("Vintage Pin Up Painting");
            c = categoryRepo.findCategoryById((long) 29);
            p19.setCategory(c);
            c = categoryRepo.findCategoryById((long) 31);
            p19.setSubcategory(c);
            p19.setDatePublishing((LocalDate.parse("15.11.2019", formatter)));
            p19.setStartDate((LocalDate.parse("1.10.2019", formatter)));
            p19.setEndDate((LocalDate.parse("8.11.2019", formatter)));
            u = userRepo.findUserById((long) 1);
            p19.setSeller(u);
            p19.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            p19.setFeature(false);
            p19.setStartPrice(50);

            productRepo.saveAll(Arrays.asList(p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19));
            logger.info("Product table seeded");
        }
        else {
            logger.trace("Product Seeding Not Required");
        }
    }
    private void seedImagesForProducts() {
        final Image ip = imageProductRepo.findImageProductById((long) 1);
        if (ip == null) {
            Product p = productRepo.findProductById((long) 4);
            Image i = new Image();
            i.setLink("https://www.affordableluxurys.com/PICTURE/154995-74-dsc_3083.jpg");
            Image i1 = new Image();
            i1.setLink("https://www.affordableluxurys.com/PICTURE/154997-74-dsc_3085.jpg");
            Image i2 = new Image();
            i2.setLink("https://www.affordableluxurys.com/PICTURE/154998-74-dsc_3086.jpg");
            Image i3 = new Image();
            i3.setLink("https://www.affordableluxurys.com/PICTURE/155003-74-dsc_3091.jpg");
            p.setImages(Arrays.asList(i, i1, i2, i3));

            Product p1 = productRepo.findProductById((long) 1);
            Image i12 = new Image();
            i12.setLink("https://assetsprx.matchesfashion.com/img/product/1319740_1_zoom.jpg");
            Image i13 = new Image();
            i13.setLink("https://assetsprx.matchesfashion.com/img/product/1319740_3_zoom.jpg");
            Image i14 = new Image();
            i14.setLink("https://assetsprx.matchesfashion.com/img/product/1319740_4_zoom.jpg");
            Image i15 = new Image();
            i15.setLink(" https://assetsprx.matchesfashion.com/img/product/1319740_6_zoom.jpg");
            p1.setImages(Arrays.asList(i12, i13, i14, i15));


            Product p2 = productRepo.findProductById((long) 2);
            Image i8 = new Image();
            i8.setLink("https://assetsprx.matchesfashion.com/img/product/1293059_1_zoom.jpg");
            Image i9 = new Image();
            i9.setLink("https://assetsprx.matchesfashion.com/img/product/1293059_4_zoom.jpg");
            Image i10 = new Image();
            i10.setLink("https://assetsprx.matchesfashion.com/img/product/1293059_5_zoom.jpg");
            Image i11 = new Image();
            i11.setLink("https://assetsprx.matchesfashion.com/img/product/1293059_6_zoom.jpg");
            p2.setImages(Arrays.asList(i8, i9, i10, i11));

            Product p3 = productRepo.findProductById((long) 3);
            Image i4 = new Image();
            i4.setLink("https://www.affordableluxurys.com/PICTURE/142672-74-dsc_2750.jpg");
            Image i5 = new Image();
            i5.setLink("https://www.affordableluxurys.com/PICTURE/142673-74-dsc_2749.jpg");
            Image i6 = new Image();
            i6.setLink(" https://www.affordableluxurys.com/PICTURE/142674-74-dsc_27561.jpg");
            Image i7 = new Image();
            i7.setLink("https://www.affordableluxurys.com/PICTURE/142675-74-dsc_2753.jpg");
            p3.setImages(Arrays.asList(i4, i5, i6, i7));

            Product p4 = productRepo.findProductById((long) 5);
            Image i16 = new Image();
            i16.setLink("https://assetsprx.matchesfashion.com/img/product/1235915_1_zoom.jpg");
            Image i17 = new Image();
            i17.setLink("https://assetsprx.matchesfashion.com/img/product/outfit_1235915_1_zoom.jpg");
            Image i18 = new Image();
            i18.setLink("https://assetsprx.matchesfashion.com/img/product/1235915_3_zoom.jpg");
            Image i19 = new Image();
            i19.setLink("https://assetsprx.matchesfashion.com/img/product/1235915_4_zoom.jpg");
            p4.setImages(Arrays.asList(i16, i17, i18, i19));

            Product p5 = productRepo.findProductById((long) 6);
            Image i20 = new Image();
            i20.setLink("https://assetsprx.matchesfashion.com/img/product/1309180_1_zoom.jpg");
            Image i21 = new Image();
            i21.setLink("https://assetsprx.matchesfashion.com/img/product/1309180_6_zoom.jpg");
            Image i22 = new Image();
            i22.setLink("https://assetsprx.matchesfashion.com/img/product/1309180_3_zoom.jpg");
            Image i23 = new Image();
            i23.setLink("https://assetsprx.matchesfashion.com/img/product/1309180_5_zoom.jpg");
            p5.setImages(Arrays.asList(i20, i21, i22, i23));

            Product p6 = productRepo.findProductById((long) 7);
            Image i24 = new Image();
            i24.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/acxwowgwxpyawi3ylaqy/air-force-1-07-womens-shoe-KyTwDPGG.jpg");
            Image i25 = new Image();
            i25.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/dd26bc4mpt2vovcsmjae/air-force-1-07-womens-shoe-KyTwDPGG.jpg");
            Image i26 = new Image();
            i26.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/up7tdgif09cvhfiuxtta/air-force-1-07-womens-shoe-KyTwDPGG.jpg");
            Image i27 = new Image();
            i27.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/lqyi72hlgpog6rianwvc/air-force-1-07-womens-shoe-KyTwDPGG.jpg");
            p6.setImages(Arrays.asList(i24, i25, i26, i27));

            Product p7 = productRepo.findProductById((long) 8);
            Image i28 = new Image();
            i28.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/xbjmntoints4we0ppil2/in-season-tr-9-womens-training-shoe-F6MRbL.jpg");
            Image i29 = new Image();
            i29.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/yyvty4s9036ifc6oheen/in-season-tr-9-womens-training-shoe-F6MRbL.jpg");
            Image i30 = new Image();
            i30.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/ff8gjxv9em4ctesubnet/in-season-tr-9-womens-training-shoe-F6MRbL.jpg");
            Image i31 = new Image();
            i31.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/imsknpfhc389kflppksi/in-season-tr-9-womens-training-shoe-F6MRbL.jpg");
            p7.setImages(Arrays.asList(i28, i29, i30, i31));

            Product p8 = productRepo.findProductById((long) 9);
            Image i32 = new Image();
            i32.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/si0paf0xsiuyvmol7hfp/bold-womens-high-support-sports-bra-plus-size-H3b8hq.jpg");
            Image i33 = new Image();
            i33.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/ttg9jjjlrxd7ext2ji5i/bold-womens-high-support-sports-bra-plus-size-H3b8hq.jpg");
            Image i34 = new Image();
            i34.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/tfphjr8iwrzvxkstrttq/bold-womens-high-support-sports-bra-plus-size-H3b8hq.jpg");
            Image i35 = new Image();
            i35.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/m15t1kfpfwhcowsjl9k4/bold-womens-high-support-sports-bra-plus-size-H3b8hq.jpg");
            p8.setImages(Arrays.asList(i32, i33, i34, i35));

            Product p9 = productRepo.findProductById((long) 10);
            Image i36 = new Image();
            i36.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/hwicurodflgja34zrmkp/nikelab-acg-down-fill-mens-parka-rNNLDZ.jpg");
            Image i37 = new Image();
            i37.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/ndbnm1iqe1zicj4jdx2a/nikelab-acg-down-fill-mens-parka-rNNLDZ.jpg");
            Image i38 = new Image();
            i38.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/su5anct2lgynl9slqybe/nikelab-acg-down-fill-mens-parka-rNNLDZ.jpg");
            Image i39 = new Image();
            i39.setLink("https://c.static-nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/gne2vlacsynl9viyfagu/nikelab-acg-down-fill-mens-parka-rNNLDZ.jpg");
            p9.setImages(Arrays.asList(i36, i37, i38, i39));

            Product p10 = productRepo.findProductById((long) 11);
            Image i40 = new Image();
            i40.setLink("https://www.redcandy.co.uk/media/catalog/product/s/t/stickerswall-stylish-rose-new_1.1527002806.gif");
            Image i41 = new Image();
            i41.setLink("https://www.redcandy.co.uk/media/catalog/product/cache/1/image/700x700/9df78eab33525d08d6e5fb8d27136e95/p/r/product-stickerswall-plum-blossom.1540912366.jpg");
            Image i42 = new Image();
            i42.setLink("https://www.redcandy.co.uk/media/catalog/product/cache/1/image/700x700/9df78eab33525d08d6e5fb8d27136e95/s/t/stickerswall-stylish-tree-and-brid.1540912366.jpg");
            Image i43 = new Image();
            i43.setLink("https://www.redcandy.co.uk/media/catalog/product/cache/1/image/700x700/9df78eab33525d08d6e5fb8d27136e95/s/p/spin-collective-cow-parsley.1540912424.jpg");
            p10.setImages(Arrays.asList(i40, i41, i42, i43));

            Product p11 = productRepo.findProductById((long) 12);
            Image i44 = new Image();
            i44.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/ipad-pro-12-11-gallery-2?wid=4056&hei=2400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1539279702007");
            Image i45 = new Image();
            i45.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/ipad-pro-12-11-gallery-3?wid=4046&hei=2400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1540330995308");
            Image i46 = new Image();
            i46.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/ipad-pro-12-11-gallery-5?wid=4056&hei=2400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1539279699888");
            Image i47 = new Image();
            i47.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/ipad-pro-12-11-gallery-4?wid=2824&hei=2400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1539279696163");
            p11.setImages(Arrays.asList(i44, i45, i46, i47));

            Product p12 = productRepo.findProductById((long) 13);
            Image i48 = new Image();
            i48.setLink("https://static.wixstatic.com/media/966455_2645a96bfbbf4ff3872cc3ce350e17e6~mv2.jpg/v1/fill/w_1160,h_840,al_c,q_85,usm_0.66_1.00_0.01/966455_2645a96bfbbf4ff3872cc3ce350e17e6~mv2.jpg");
            Image i49 = new Image();
            i49.setLink("https://static.wixstatic.com/media/966455_b3fcf5418a0c4fb283fbc62d7dbecaad~mv2.jpg/v1/fill/w_780,h_630,al_c,q_85,usm_0.66_1.00_0.01/966455_b3fcf5418a0c4fb283fbc62d7dbecaad~mv2.webp");
            Image i50 = new Image();
            i50.setLink("https://static.wixstatic.com/media/966455_ff2d8641e50048e2ac6d66758deea7e3~mv2.jpg/v1/fill/w_780,h_630,al_c,q_85,usm_0.66_1.00_0.01/966455_ff2d8641e50048e2ac6d66758deea7e3~mv2.webp");
            Image i51 = new Image();
            i51.setLink("https://static.wixstatic.com/media/966455_cfd4987c010144499df20075ecd91d3d~mv2.jpg/v1/fill/w_780,h_630,al_c,q_85,usm_0.66_1.00_0.01/966455_cfd4987c010144499df20075ecd91d3d~mv2.webp");
            p12.setImages(Arrays.asList(i48, i49, i50, i51));

            Product p13 = productRepo.findProductById((long) 14);
            Image i52 = new Image();
            i52.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MQGK2?wid=572&hei=572&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1536875850163");
            Image i53 = new Image();
            i53.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MQGK2_AV1_SPACE_GRAY_GEO_US?wid=572&hei=572&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1547667673145");
            Image i54 = new Image();
            i54.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MQGK2_AV1_GOLD_GEO_US?wid=572&hei=572&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1547667505344");
            Image i55 = new Image();
            i55.setLink("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MQGK2_AV2_GEO_US?wid=572&hei=572&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1547667726318");
            p13.setImages(Arrays.asList(i52, i53, i54, i55));

            Product p14 = productRepo.findProductById((long) 15);
            Image i56 = new Image();
            i56.setLink("https://cdn.macrumors.com/article-new/2017/06/iphone-8-usb-c-wall-charger-800x541.jpg");
            Image i57 = new Image();
            i57.setLink("https://images-na.ssl-images-amazon.com/images/I/61Ek1qTFjQL._SL1500_.jpg");
            Image i58 = new Image();
            i58.setLink("https://timedotcom.files.wordpress.com/2017/01/iphone6s.jpg?w=600&quality=85");
            Image i59 = new Image();
            i59.setLink("https://www.howtogeek.com/wp-content/uploads/2017/12/img_5a25e49e2a359.jpg");
            p14.setImages(Arrays.asList(i56, i57, i58, i59));

            Product p15 = productRepo.findProductById((long) 16);
            Image i60 = new Image();
            i60.setLink("https://ae01.alicdn.com/kf/HTB13pHuPXXXXXXAXFXXq6xXFXXXp/1600-DPI-USB-Optical-Wireless-Computer-Mouse-2-4G-Receiver-Super-Slim-Mouse-For-PC-Laptop.jpg");
            Image i61 = new Image();
            i61.setLink("https://images-na.ssl-images-amazon.com/images/I/41DO-PDwJ6L._SX425_.jpg");
            Image i62 = new Image();
            i62.setLink("https://cdn.shopify.com/s/files/1/2955/8090/products/1600-dpi-usb-optical-wireless-computer-mouse-24g-receiver-super-slim-mouse-for-pc-laptop-desktop-computer-peripherals-guangzhou-newin-technology-coltd-white-7.jpg?v=1528510710");
            Image i63 = new Image();
            i63.setLink("https://www.dhresource.com/0x0/f2/albu/g4/M01/86/21/rBVaEVn7zPKAdJvQAAM_l_6YKNY681.jpg");
            p15.setImages(Arrays.asList(i60, i61, i62, i63));

            Product p16 = productRepo.findProductById((long) 17);
            Image i64 = new Image();
            i64.setLink("https://expresstabloid.ba/wp-content/uploads/2018/03/bijelo_dugme_album.png");
            Image i65 = new Image();
            i65.setLink("https://www.njuskalo.hr/image-w920x690/gramofonske-ploce/bijelo-dugme-brdo-ploca-potpis-alena-islamovica-slika-110340691.jpg");
            Image i66 = new Image();
            i66.setLink("https://upload.wikimedia.org/wikipedia/bs/thumb/8/8c/Singl_plo%C4%8De_%281974-1975%29_%28Bijelo_Dugme%29.jpg/220px-Singl_plo%C4%8De_%281974-1975%29_%28Bijelo_Dugme%29.jpg");
            Image i67 = new Image();
            i67.setLink("https://upload.wikimedia.org/wikipedia/bs/0/07/Singl_plo%C4%8De_%281976-1980%29_%28Bijelo_dugme%29.jpg");
            p16.setImages(Arrays.asList(i64, i65, i66, i67));

            Product p17 = productRepo.findProductById((long) 18);
            Image i68 = new Image();
            i68.setLink("https://img.discogs.com/8l6OV9lIoWDOQnnjG_Xp9rCTfB4=/fit-in/600x526/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-9430347-1480561717-9683.jpeg.jpg");
            Image i69 = new Image();
            i69.setLink("https://img.cdandlp.com/2015/11/imgL/117784071.jpeg");
            Image i70 = new Image();
            i70.setLink("https://i.pinimg.com/originals/3a/f0/e5/3af0e55ea66ea69e35145fb108b4a636.jpg");
            Image i71 = new Image();
            i71.setLink("https://shop.rapbay.com/images/products/699487220027.jpg");
            p17.setImages(Arrays.asList(i68, i69, i70, i71));

            Product p18 = productRepo.findProductById((long) 19);
            Image i72 = new Image();
            i72.setLink("https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg");
            Image i73 = new Image();
            i73.setLink("https://i.redd.it/776mt4w2r7j31.jpg");
            Image i74 = new Image();
            i74.setLink("https://i.redd.it/46glavkeizp21.jpg");
            Image i75 = new Image();
            i75.setLink("https://pbs.twimg.com/media/EA4LLfsW4AErVjR.jpg");
            p18.setImages(Arrays.asList(i72, i73, i74, i75));

            Product p19 = productRepo.findProductById((long) 20);
            Image i76 = new Image();
            i76.setLink("https://images.fineartamerica.com/images/artworkimages/mediumlarge/2/smoke-screen-vintage-pin-up-girl-gil-elvgren.jpg");
            Image i77 = new Image();
            i77.setLink("https://images.fineartamerica.com/images-medium-large-5/2-vintage-pin-up-vintage-canvas.jpg");
            Image i78 = new Image();
            i78.setLink("https://www.dhresource.com/0x0/f2/albu/g5/M01/8E/53/rBVaJFhjBSaAOtzBAAFXTbTqMjY531.jpg");
            Image i79 = new Image();
            i79.setLink("http://www.thepinupfiles.com/runci/RUNCI_img_22.jpg");
            p19.setImages(Arrays.asList(i76, i77, i78, i79));







            productRepo.saveAll(Arrays.asList(p, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16,
                    p17, p18, p19));

            imageProductRepo.saveAll(Arrays.asList(i, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13,
                    i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27, i28, i29, i30, i31,
                    i32, i33, i34, i35, i36, i37, i38, i39, i40, i41, i42, i43, i44, i45, i46, i47, i48, i49,
                    i50, i51, i52, i53, i54, i55, i56, i57, i58, i59, i60, i61, i62, i63, i64, i65, i66, i67, i68,
                    i69, i70, i71, i72, i73, i74, i75, i76, i77, i78, i79));
            logger.info("ImageProduct table seeded");
        }
        else {
            logger.trace("ImageProduct Seeding Not Required");
        }


    }
}
