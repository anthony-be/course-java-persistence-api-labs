package be.cocoding.jpa.exercise.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class SpringDaoContextTest {

    @Test
    public void load(){
        // Ne rien faire ici.
        // Juste vérifier que le context Spring (de test) se charge correctement
    }

}
