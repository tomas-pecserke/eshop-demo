package sk.garwan.pecserke.eshop.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import sk.garwan.pecserke.eshop.product.model.CreateProductDto;
import sk.garwan.pecserke.eshop.product.model.ProductDetailDto;
import sk.garwan.pecserke.eshop.product.model.ProductDto;
import sk.garwan.pecserke.eshop.product.service.ProductService;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
public class ProductController {
    private final ProductService productService;
    private final int defaultPageSize;

    public ProductController(ProductService productService, @Value("${eshop.pagination.defaultPageSize}") int defaultPageSize) {
        this.productService = productService;
        this.defaultPageSize = defaultPageSize;
    }

    @GetMapping(path = "/products")
    public Page<ProductDto> list(
        @RequestParam(name = "page", defaultValue = "0") @PositiveOrZero int pageNumber,
        @RequestParam(name = "size", defaultValue = "0") @PositiveOrZero int pageSize,
        @RequestParam(name = "name", defaultValue = "") String nameStartsWith,
        @RequestParam(name = "min", defaultValue = "0") @PositiveOrZero double minPrice,
        @RequestParam(name = "max", required = false) @PositiveOrZero @Nullable Double maxPrice
    ) {
        return productService.getProducts(
            pageNumber,
            pageSize == 0 ? defaultPageSize : pageSize,
            minPrice,
            maxPrice == null ? Double.MAX_VALUE : maxPrice,
            nameStartsWith
        );
    }

    @GetMapping("/products/{id}")
    @Transactional
    public ResponseEntity<ProductDetailDto> detail(@PathVariable long id) {
        return productService.getProductDetail(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("ADMIN")
    @Transactional
    public void create(@RequestBody @Valid CreateProductDto createProduct) {
        productService.createProduct(
            createProduct.getName(),
            createProduct.getDescription(),
            createProduct.getPrice(),
            createProduct.getCategories(),
            createProduct.getGallery()
        );
    }
}
