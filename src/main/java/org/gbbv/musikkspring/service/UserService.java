package org.gbbv.musikkspring.service;

import org.gbbv.musikkspring.dto.UserDto;
import org.gbbv.musikkspring.model.Role;
import org.gbbv.musikkspring.model.RoleEnum;
import org.gbbv.musikkspring.model.User;


import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
//    void updateUserRole(String email, String roleName);
}
