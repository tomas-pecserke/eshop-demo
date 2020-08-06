package sk.garwan.pecserke.eshop.identity.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.identity.model.UserDto;
import sk.garwan.pecserke.eshop.identity.persistance.User;

import static java.util.Objects.requireNonNull;

@Component
public class UserMapper implements DtoMapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        return new UserDto(requireNonNull(user.getId()), user.getUsername());
    }
}
