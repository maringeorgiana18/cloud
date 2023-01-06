package com.backend.mapper;

import com.backend.model.user.AddUserModel;
import com.backend.model.user.GetUserModel;
import com.backend.model.user.UpdateUserModel;
import com.backend.security.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    User updateUserModelMapperToUser(UpdateUserModel updateUserModel);
    User addUserModelToUser(AddUserModel addUserModel);
    GetUserModel userToGetUserModel(User user);
    List<GetUserModel> usersToGetUserModels(List<User> user);
}
