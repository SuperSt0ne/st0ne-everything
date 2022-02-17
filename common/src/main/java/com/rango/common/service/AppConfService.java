package com.rango.common.service;

import com.rango.common.dto.AppConfDTO;

public interface AppConfService {

    AppConfDTO getByConfKey(String confKey);

}
