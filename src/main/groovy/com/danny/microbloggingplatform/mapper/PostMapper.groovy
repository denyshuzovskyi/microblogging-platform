package com.danny.microbloggingplatform.mapper

import com.danny.microbloggingplatform.dto.PostCreationDTO
import com.danny.microbloggingplatform.model.Post
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metaClass", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post PostCreationDTOToPost(PostCreationDTO postCreationDTO)
}