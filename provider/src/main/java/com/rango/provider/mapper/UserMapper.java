package com.rango.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rango.provider.dataObject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    UserDO getUserById(Long id);
}
