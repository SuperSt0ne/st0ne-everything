package com.rango.provider.controller;

import com.rango.basic.result.RangoResult;
import com.rango.basic.result.ResultMessage;
import com.rango.common.dto.UserDTO;
import com.rango.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public RangoResult<UserDTO> user(@PathVariable Long userId) {
        RangoResult<UserDTO> result = new RangoResult<>();
        if (Objects.isNull(userId)) {
            result.setErrorMsg(ResultMessage.PARAM_EXCEPTION);
            return result;
        }
        UserDTO user = userService.getUserById(userId);
        if (Objects.isNull(user)) {
            result.setErrorMsg(ResultMessage.PARAM_EXCEPTION);
            return result;
        }
        result.setData(user);
        return result;
    }

    @PostMapping("/user")
    public RangoResult<Integer> user(UserDTO user) {
        RangoResult<Integer> result = new RangoResult<>();
        result.setData(userService.insert(user));
        return result;
    }
}
