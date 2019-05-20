package com.sinosoft.newstandard.coreservice.service.impl;

import com.sinosoft.newstandard.common.web.entity.User;
import com.sinosoft.newstandard.common.web.service.impl.UserServiceImpl;
import com.sinosoft.newstandard.coreservice.service.CoreIUserService;
import com.sinosoft.newstandard.database.druid.multipledatasource.DataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
@Transactional
@DataSource("other")
public class OtherUserServiceImpl extends UserServiceImpl implements CoreIUserService {

    @Override
    public void insertTest() {
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setNameCn("other");
            if (i == 1) {
                //System.out.println(1 / 0);
            }
            getMapper().insert(user);
        }
    }

}
