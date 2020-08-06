package sk.garwan.pecserke.eshop.identity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class UserProvider implements Supplier<Optional<User>> {
    private final UserRepository userRepository;

    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<User> get() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        return userRepository.findByUsername(auth.getName());
    }
}
