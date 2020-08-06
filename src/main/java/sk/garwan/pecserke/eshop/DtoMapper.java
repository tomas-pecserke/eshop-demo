package sk.garwan.pecserke.eshop;

import java.util.List;
import java.util.stream.Collectors;

public interface DtoMapper <E, D> {
    D map(E entity);

    default List<D> mapList(List<E> list) {
        return list.stream().map(this::map).collect(Collectors.toList());
    }
}
