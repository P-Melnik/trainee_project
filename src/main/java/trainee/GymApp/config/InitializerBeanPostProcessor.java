package trainee.GymApp.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import trainee.GymApp.storage.Storage;

@Component
public class InitializerBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof Storage) {
            try {
                ((Storage) bean).initializeData();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
