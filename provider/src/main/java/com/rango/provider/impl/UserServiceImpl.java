package com.rango.provider.impl;

import com.rango.basic.bean.BeanCopy;
import com.rango.common.dto.UserDTO;
import com.rango.common.service.UserService;
import com.rango.provider.dataObject.UserDO;
import com.rango.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(UserDTO user) {
        return userMapper.insert(BeanCopy.dtoConvertDo(user, UserDO.class));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return BeanCopy.doConvertDto(userMapper.getUserById(id), UserDTO.class);
//        return userMapper.getUserById(id);
    }
}
