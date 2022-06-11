package be.cocoding.jpa.exercise.app;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

public class Client {

    private final ApplicationContext appCtx;

    public Client(ClassPathXmlApplicationContext appCtx) {
        this.appCtx = appCtx;
    }

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/application-context.xml");

        // *** Create category ***
        //Client client = new Client(appCtx);
        //client.createCategoryNull();

        // *** Update category name -- Concurrent ***
        // Create and run new Threads for parallel/concurrent update
        //Client clientA = new Client(appCtx);
        //Client clientB = new Client(appCtx);
        //new Thread(() -> clientA.updateCategoryName(1, "Category ClientA name")).start();
        //new Thread(() -> clientB.updateCategoryName(1, "Category ClientB name")).start();

        // Check category name
        //Thread.sleep(2000L); // Attendre que les clients aient finis
        //Category category = clientA.findCategoryById(1);
        //System.out.println("Category name: " + category.getName());

        // *** Find all products -- Caching ***
        Client client = new Client(appCtx);
        client.findAllProducts(); // 1
        client.findAllProducts(); // 2
        client.findAllProducts(); // 3
        client.findAllProducts(); // 4
        client.findAllProducts(); // 5

        client.findProductById(1);
        client.findProductById(1);
        client.findProductById(1);
        client.findProductById(1);
    }


    private void findCustomerById(){
        // TODO Implement me !
    }

    private void createCategoryMouse(){
        ShopManager shopManager = getShopManager();
        shopManager.createCategory("Mouse", "The Mouse category's description");
    }

    private void createCategoryNull(){
        ShopManager shopManager = getShopManager();
        shopManager.createCategory(null, null);
    }

    public void updateCategoryName(int catId, String name) {
        ShopManager shopManager = getShopManager();
        shopManager.updateCategoryName(catId, name);
    }

    private Category findCategoryById(int categoryId){
        return getShopManager().findCategoryById(categoryId);
    }

    private void findAllProducts(){
        ShopManager shopManager = getShopManager();
        Collection<Product> allProducts = shopManager.findAllProducts();
        System.out.println("Nbr of products: " + allProducts.size());
    }

    private Product findProductById(int productId){
        ShopManager shopManager = getShopManager();
        return shopManager.findProductById(productId);
    }

    private ShopManager getShopManager(){
        return appCtx.getBean("shopManager", ShopManager.class);
    }
}
