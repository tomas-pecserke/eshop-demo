package sk.garwan.pecserke.eshop.identity.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.model.SecurityUserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;

import static java.util.Objects.requireNonNull;

@Component
public class SecurityUserMapper implements DtoMapper<User, SecurityUserDto> {
    @Override
    public SecurityUserDto map(User user) {
        return new SecurityUserDto(
            requireNonNull(user.getId()),
            user.getUsername(),
            user.getPassword(),
            user.isAdmin()
        );
    }
}
