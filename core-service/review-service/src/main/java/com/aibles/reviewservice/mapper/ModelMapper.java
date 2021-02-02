package com.aibles.reviewservice.mapper;

import com.aibles.reviewservice.entity.Review;
import dtos.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mappings({
            @Mapping(target = "uid", source = "uid"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    ReviewDTO convertToDTO(Review review);

    List<ReviewDTO> convertToDTOList(List<Review> reviews);
}
