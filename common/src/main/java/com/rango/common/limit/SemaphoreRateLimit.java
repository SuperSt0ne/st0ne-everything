package com.rango.common.limit;

import com.rango.basic.common.CommonConstant;
import com.rango.common.dto.AppConfDTO;
import com.rango.common.service.AppConfService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Log4j2
public class SemaphoreRateLimit implements InitializingBean, Serializable {

    private static final long serialVersionUID = -2550920199525641054L;

    public static final Map<String, Semaphore> URL_LIMIT_MAP = new ConcurrentHashMap<>();

    private static final Integer DEFAULT_LIMIT = 1;

    private static final String LIMIT_URL_CONF = "limit_url_conf";

    @Autowired
    private AppConfService appConfService;

    public Boolean acquire(String url) {
        if (StringUtils.isBlank(url)) return Boolean.FALSE;

        if (notInConfLimit(url)) return Boolean.TRUE;

        Semaphore semaphore = URL_LIMIT_MAP.get(url);
        if (Objects.isNull(semaphore)) {
            semaphore = new Semaphore(DEFAULT_LIMIT);
            URL_LIMIT_MAP.put(url, semaphore);
        }
        try {
            if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                log.error(String.format("%s:%s semaphore acquire success", Thread.currentThread().getName(), url));
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return Boolean.FALSE;
        }
    }

    public void release(String url) {
        if (StringUtils.isBlank(url) || notInConfLimit(url)) return;

        Semaphore semaphore = URL_LIMIT_MAP.get(url);
        if (Objects.isNull(semaphore)) {
            return;
        }
        semaphore.release(1);
        log.error(String.format("%s:%s semaphore release success", Thread.currentThread().getName(), url));
    }

    @Override
    public void afterPropertiesSet() {
        AppConfDTO conf = appConfService.getByConfKey(LIMIT_URL_CONF);
        String confVal = conf.getConfVal();
        if (StringUtils.isBlank(confVal)) {
            return;
        }
        for (String confUrl : confVal.split(CommonConstant.COMMA)) {
            URL_LIMIT_MAP.put(confUrl, new Semaphore(DEFAULT_LIMIT));
        }
    }

    private boolean notInConfLimit(String url) {
        return !URL_LIMIT_MAP.containsKey(url);
    }

}
