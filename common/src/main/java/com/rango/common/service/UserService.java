package com.rango.common.service;

import com.rango.common.dto.UserDTO;

public interface UserService {

    int insert(UserDTO user);

    UserDTO getUserById(Long id);
}
