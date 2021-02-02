package com.aibles.productservice.mapper;

import com.aibles.productservice.entity.Product;
import dtos.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mappings(
            @Mapping(target = "uid", source = "uid")
    )
    ProductDTO convertToDTO(Product product);

    List<ProductDTO> convertToDTOs(List<Product> products);

}
