package sk.garwan.pecserke.eshop.identity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sk.garwan.pecserke.eshop.identity.model.RegisterUserDto;
import sk.garwan.pecserke.eshop.identity.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void register(@RequestBody @Valid RegisterUserDto registerUser) {
        userService.createUser(
            registerUser.getUsername(),
            registerUser.getEmail(),
            false,
            registerUser.getPassword()
        );
    }
}
