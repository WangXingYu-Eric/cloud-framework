package com.sinosoft.newstandard.coreservice.service.impl;

import com.sinosoft.newstandard.common.web.entity.User;
import com.sinosoft.newstandard.common.web.service.impl.UserServiceImpl;
import com.sinosoft.newstandard.coreservice.service.CoreIUserService;
import com.sinosoft.newstandard.database.druid.multipledatasource.DataSource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
@Transactional
@DataSource("base")
@CacheConfig(cacheNames = "test")
public class BaseUserServiceImpl extends UserServiceImpl implements CoreIUserService {

    @Override
    @Cacheable(key = "1")
    public List<User> selectAll() {
        return super.selectAll();
    }


    @Cacheable(value = "test", key = "2")
    public String selectTest(String num) {
        Random random = new Random();
        final int i = random.nextInt(100);
        return String.valueOf(i);
    }

    @Override
    public void insertTest() {
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setNameCn("base");
            if (i == 1) {
                //System.out.println(1 / 0);
            }
            getMapper().insert(user);
        }
    }

}
