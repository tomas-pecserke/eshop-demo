package sk.garwan.pecserke.eshop.product.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public class ImageDto {
    @NotBlank
    @URL
    @Length(max = 255)
    private String url;

    @NotBlank
    @Length(max = 255)
    private String title;

    @Nullable
    @Length(max = 4000)
    private String description;

    public ImageDto() {
        this("", "", null);
    }

    public ImageDto(String url, String title, @Nullable String description) {
        this.url = url;
        this.title = title;
        this.description = description;
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
