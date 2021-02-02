package com.aibles.categoryservice.mapper;

import com.aibles.categoryservice.entity.Category;
import dtos.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mappings(
            @Mapping(target = "uid", source = "uid")
    )
    CategoryDTO convertToDTO(Category category);

    List<CategoryDTO> convertToDTOs(List<Category> categories);
}
