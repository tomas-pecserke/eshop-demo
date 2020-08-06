package sk.garwan.pecserke.eshop.product.mapper;

import org.springframework.stereotype.Component;
import sk.garwan.pecserke.eshop.product.model.ImageDto;
import sk.garwan.pecserke.eshop.product.persistance.Image;
import sk.garwan.pecserke.eshop.DtoMapper;

@Component
public class ImageMapper implements DtoMapper<Image, ImageDto> {
    @Override
    public ImageDto map(Image image) {
        return new ImageDto(image.getUrl(), image.getTitle(), image.getDescription());
    }
}
