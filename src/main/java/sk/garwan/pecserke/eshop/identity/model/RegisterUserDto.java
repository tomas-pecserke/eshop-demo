package sk.garwan.pecserke.eshop.identity.model;

import org.hibernate.validator.constraints.Length;
import sk.garwan.pecserke.eshop.identity.constraints.UniqueEmail;
import sk.garwan.pecserke.eshop.identity.constraints.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterUserDto {
    @NotBlank
    @Length(max = 255)
    @UniqueUsername
    private String username = "";

    @Email
    @NotBlank
    @Length(max = 255)
    @UniqueEmail
    private String email = "";

    @NotBlank
    @Length(min = 8)
    private String password = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
