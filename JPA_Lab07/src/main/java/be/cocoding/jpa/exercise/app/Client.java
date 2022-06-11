package be.cocoding.jpa.exercise.app;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

    private final ApplicationContext appCtx;

    public Client(ClassPathXmlApplicationContext appCtx) {
        this.appCtx = appCtx;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/application-context.xml");
        Client client = new Client(appCtx);
        client.findCustomerById();
    }


    private void findCustomerById(){
        // TODO Implement me !
    }

    private Object getShopManager(){
        return appCtx.getBean("shopManager", Object.class);//TODO ShopManager class
    }
}
