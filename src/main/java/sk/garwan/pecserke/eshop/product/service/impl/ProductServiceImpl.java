package sk.garwan.pecserke.eshop.product.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.DtoMapper;
import sk.garwan.pecserke.eshop.product.model.ImageDto;
import sk.garwan.pecserke.eshop.product.model.ProductDetailDto;
import sk.garwan.pecserke.eshop.product.model.ProductDto;
import sk.garwan.pecserke.eshop.product.persistance.AnimalCategoryRepository;
import sk.garwan.pecserke.eshop.product.persistance.Image;
import sk.garwan.pecserke.eshop.product.persistance.Product;
import sk.garwan.pecserke.eshop.product.persistance.ProductRepository;
import sk.garwan.pecserke.eshop.product.service.ProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final AnimalCategoryRepository animalCategoryRepository;
    private final DtoMapper<Product, ProductDto> productMapper;
    private final DtoMapper<Product, ProductDetailDto> productDetailMapper;

    public ProductServiceImpl(
        ProductRepository productRepository,
        AnimalCategoryRepository animalCategoryRepository,
        DtoMapper<Product, ProductDto> productMapper,
        DtoMapper<Product, ProductDetailDto> productDetailMapper
    ) {
        this.productRepository = productRepository;
        this.animalCategoryRepository = animalCategoryRepository;
        this.productMapper = productMapper;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    @Transactional
    public Page<ProductDto> getProducts(
        int pageNumber,
        int pageSize,
        double minPrice,
        double maxPrice,
        String nameStartsWith
    ) {
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "name");
        var page = productRepository.findAllByPriceBetweenAndNameStartsWith(
            pageRequest,
            minPrice,
            maxPrice,
            nameStartsWith);

        return page.map(productMapper::map);
    }

    @Override
    @Transactional
    public Optional<ProductDetailDto> getProductDetail(long productId) {
        return productRepository.findById(productId).map(productDetailMapper::map);
    }

    @Override
    @Transactional
    public ProductDetailDto createProduct(
        String name,
        @Nullable String description,
        double price,
        Set<String> categories,
        List<ImageDto> gallery
    ) {
        var product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        for (var category : categories) {
            product.getCategories().add(animalCategoryRepository.findOneByName(category));
        }
        product.setGallery(gallery.stream().map(
            imageDto -> new Image(imageDto.getUrl(), imageDto.getTitle(), imageDto.getDescription())
        ).collect(Collectors.toList()));
        product = productRepository.saveAndFlush(product);

        return productDetailMapper.map(product);
    }
}
