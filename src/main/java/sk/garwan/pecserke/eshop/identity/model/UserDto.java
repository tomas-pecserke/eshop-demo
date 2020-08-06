package sk.garwan.pecserke.eshop.identity.model;

public class UserDto {
    private final long id;
    private final String username;

    public UserDto(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
