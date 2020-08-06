package sk.garwan.pecserke.eshop.identity.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.model.UserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;
import sk.garwan.pecserke.eshop.identity.service.UserService;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoMapper<User, UserDto> userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        DtoMapper<User, UserDto> userMapper,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(String username, String email, boolean admin, String password) {
        var user = new User();
        user.setUsername(username);
        user.setEmail(username + "@example.com");
        user.setAdmin(admin);
        user.setPassword(passwordEncoder.encode(username));
        user = userRepository.saveAndFlush(user);

        return userMapper.map(user);
    }
}
