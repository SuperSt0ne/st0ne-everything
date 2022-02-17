package com.rango.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rango.common.dto.AppConfDTO;
import com.rango.provider.dataObject.AppConfDO;
import com.rango.provider.dataObject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppConfMapper extends BaseMapper<AppConfDO> {

    AppConfDO getByConfKey(String confKey);

}
