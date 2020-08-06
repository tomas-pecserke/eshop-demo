package sk.garwan.pecserke.eshop.product.persistance;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class AnimalCategory {
    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Length(max = 255)
    private String name;

    public AnimalCategory() {
        this("");
    }

    public AnimalCategory(String name) {
        this.name = name;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
