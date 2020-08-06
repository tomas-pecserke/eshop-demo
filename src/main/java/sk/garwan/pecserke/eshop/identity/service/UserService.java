package sk.garwan.pecserke.eshop.identity.service;

import sk.garwan.pecserke.eshop.identity.model.UserDto;

public interface UserService {
    UserDto createUser(String username, String email, boolean admin, String password);
}
