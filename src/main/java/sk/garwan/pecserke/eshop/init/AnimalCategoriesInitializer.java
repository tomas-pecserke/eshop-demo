package sk.garwan.pecserke.eshop.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.product.persistance.AnimalCategory;
import sk.garwan.pecserke.eshop.product.persistance.AnimalCategoryRepository;

import javax.transaction.Transactional;

@Component
public class AnimalCategoriesInitializer implements InitializingBean {
    private static final String[] categories = new String[] {
        "cats",
        "dogs",
        "other"
    };

    private final AnimalCategoryRepository repository;

    public AnimalCategoriesInitializer(AnimalCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        var needToFlush = false;
        for (var name : categories) {
            if (repository.findByName(name).isEmpty()) {
                var category = new AnimalCategory();
                category.setName(name);
                repository.save(category);
                needToFlush = true;
            }
        }
        if (needToFlush) {
            repository.flush();
        }
    }
}
