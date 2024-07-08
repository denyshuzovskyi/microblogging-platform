package com.danny.microbloggingplatform.mapper

import com.danny.microbloggingplatform.dto.UserCreationDTO
import com.danny.microbloggingplatform.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metaClass", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "following", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User userCreationDTOToUser(UserCreationDTO userCreationDTO)
}