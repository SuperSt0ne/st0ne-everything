package com.rango.provider.impl;

import com.rango.basic.bean.BeanCopy;
import com.rango.common.dto.AppConfDTO;
import com.rango.common.service.AppConfService;
import com.rango.provider.mapper.AppConfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConfServiceImpl implements AppConfService {

    @Autowired
    private AppConfMapper appConfMapper;

    @Override
    public AppConfDTO getByConfKey(String confKey) {
        return BeanCopy.doConvertDto(appConfMapper.getByConfKey(confKey), AppConfDTO.class);
    }
}
