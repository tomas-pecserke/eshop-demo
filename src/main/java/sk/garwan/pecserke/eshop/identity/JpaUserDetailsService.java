package sk.garwan.pecserke.eshop.identity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.model.SecurityUserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;
import sk.garwan.pecserke.eshop.identity.persistance.UserRepository;

public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DtoMapper<User, SecurityUserDto> securityUserMapper;

    public JpaUserDetailsService(
        UserRepository userRepository,
        DtoMapper<User, SecurityUserDto> securityUserMapper
    ) {
        this.userRepository = userRepository;
        this.securityUserMapper = securityUserMapper;
    }

    @Override
    public SecurityUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(securityUserMapper::map)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
