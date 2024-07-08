package com.danny.microbloggingplatform.mapper

import com.danny.microbloggingplatform.dto.CommentCreationDTO
import com.danny.microbloggingplatform.model.Comment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CommentMapper {
    @Mapping(target = "metaClass", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment CommentCreationDTOToComment(CommentCreationDTO commentCreationDTO)
}