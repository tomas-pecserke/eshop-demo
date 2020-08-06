package sk.garwan.pecserke.eshop.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;

@Component
public class SeedUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedUtils(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username) {
        var user = new User();
        user.setUsername(username);
        user.setEmail(username + "@example.com");
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(username));

        return userRepository.saveAndFlush(user);
    }
}
