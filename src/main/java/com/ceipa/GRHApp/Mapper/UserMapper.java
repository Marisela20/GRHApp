package com.ceipa.GRHApp.Mapper;

import com.ceipa.GRHApp.DatabaseEntity.UserEntity;
import com.ceipa.GRHApp.Model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    List<UserAccount> userEntityListToUserList(List<UserEntity> userEntities);

    UserEntity userModelToUserEntity(UserAccount userAccount);

    // ✅ Esto vincula el UserEntity original al campo 'entity' del modelo
    @Mapping(target = "entity", source = ".")
    UserAccount userEntityToUser(UserEntity userEntity);
}
