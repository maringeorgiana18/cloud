package com.backend.mapper;

import com.backend.model.user.AddUserModel;
import com.backend.model.user.GetUserModel;
import com.backend.model.user.UpdateUserModel;
import com.backend.security.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T20:58:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User updateUserModelMapperToUser(UpdateUserModel updateUserModel) {
        if ( updateUserModel == null ) {
            return null;
        }

        User user = new User();

        user.setId( updateUserModel.getId() );
        user.setUserName( updateUserModel.getUserName() );
        user.setPassword( updateUserModel.getPassword() );
        user.setEmail( updateUserModel.getEmail() );

        return user;
    }

    @Override
    public User addUserModelToUser(AddUserModel addUserModel) {
        if ( addUserModel == null ) {
            return null;
        }

        User user = new User();

        user.setUserName( addUserModel.getUserName() );
        user.setPassword( addUserModel.getPassword() );
        user.setEmail( addUserModel.getEmail() );

        return user;
    }

    @Override
    public GetUserModel userToGetUserModel(User user) {
        if ( user == null ) {
            return null;
        }

        GetUserModel getUserModel = new GetUserModel();

        getUserModel.setId( user.getId() );
        getUserModel.setUserName( user.getUserName() );
        getUserModel.setEmail( user.getEmail() );
        getUserModel.setRole( user.getRole() );
        getUserModel.setEnable( user.isEnable() );

        return getUserModel;
    }

    @Override
    public List<GetUserModel> usersToGetUserModels(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<GetUserModel> list = new ArrayList<GetUserModel>( user.size() );
        for ( User user1 : user ) {
            list.add( userToGetUserModel( user1 ) );
        }

        return list;
    }
}
