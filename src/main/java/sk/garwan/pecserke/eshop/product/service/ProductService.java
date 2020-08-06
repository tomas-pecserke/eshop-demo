package sk.garwan.pecserke.eshop.product.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import sk.garwan.pecserke.eshop.product.model.ImageDto;
import sk.garwan.pecserke.eshop.product.model.ProductDetailDto;
import sk.garwan.pecserke.eshop.product.model.ProductDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Page<ProductDto> getProducts(int pageNumber, int pageSize, double minPrice, double maxPrice, String nameStartsWith);
    Optional<ProductDetailDto> getProductDetail(long productId);
    ProductDetailDto createProduct(
        String name,
        @Nullable String description,
        double price,
        Set<String> categories,
        List<ImageDto> gallery
    );
}
