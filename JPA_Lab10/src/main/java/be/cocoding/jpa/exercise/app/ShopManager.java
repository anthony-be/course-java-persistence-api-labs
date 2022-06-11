package be.cocoding.jpa.exercise.app;

import be.cocoding.jpa.exercise.dao.ProductDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Random;

@Component
@Transactional
public class ShopManager {

    private Logger logger = LoggerFactory.getLogger(ShopManager.class);

    @Autowired
    private ProductDao productDao;

    public Category createCategory(String name, String description){
        if(StringUtils.isEmpty(name)){throw new IllegalArgumentException("Given 'name' parameter cannot be null or empty");}
        if(StringUtils.isEmpty(description)){throw new IllegalArgumentException("Given 'description' parameter cannot be null or empty");}
        return productDao.addProductCategory(name, description);
    }

    public void updateCategoryName(Integer catId, String name){
        logger.info("Updating category with id '{}' for new name: {}", catId, name);
        Category category = productDao.findCategoryById(catId);
        category.setName(name);
        sleep(new Random().nextInt(1500));// Sleep for max 1500 ms
    }

    private void sleep(long time){
        try {
            logger.info("Sleeping for (ms): {}", time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findCategoryById(int categoryId) {
        return productDao.findCategoryById(categoryId);
    }
}
