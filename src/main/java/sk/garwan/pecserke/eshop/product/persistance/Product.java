package sk.garwan.pecserke.eshop.product.persistance;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Nullable
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Length(max = 255)
    private String name;

    @Lob
    @Nullable
    private String description;

    @PositiveOrZero
    @Column(nullable = false)
    private double price;

    @ManyToMany
    @JoinTable(
        joinColumns = @JoinColumn(nullable = false),
        inverseJoinColumns = @JoinColumn(nullable = false)
    )
    @NotEmpty
    private Set<AnimalCategory> categories;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        joinColumns = @JoinColumn(nullable = false),
        inverseJoinColumns = @JoinColumn(nullable = false)
    )
    private List<Image> gallery;

    public Product() {
        categories = new HashSet<>();
        gallery = new ArrayList<>();
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

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<AnimalCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<AnimalCategory> animalCategories) {
        this.categories = animalCategories;
    }

    public List<Image> getGallery() {
        return gallery;
    }

    public void setGallery(List<Image> gallery) {
        this.gallery = gallery;
    }
}
