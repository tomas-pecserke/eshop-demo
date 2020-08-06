package sk.garwan.pecserke.eshop.product.persistance;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Image {
    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Length(max = 255)
    @URL
    private String url;

    @Column(nullable = false)
    @NotBlank
    @Length(max = 255)
    private String title;

    @Nullable
    @Column(length = 4000)
    @Length(max = 4000)
    private String description;

    public Image() {
        this("", "", null);
    }

    public Image(String url, String title, @Nullable String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
