package sk.garwan.pecserke.eshop.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;
import sk.garwan.pecserke.eshop.identity.service.UserService;

import javax.transaction.Transactional;

@Component
public class AdminInitializer implements InitializingBean {
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminInitializer(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() {
        var admin = userRepository.findByUsername("admin");
        if (admin.isEmpty()) {
            userService.createUser("admin", "admin@example.com", true, "admin");
        }
    }
}
