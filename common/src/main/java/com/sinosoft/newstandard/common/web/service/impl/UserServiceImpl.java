package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.User;
import com.sinosoft.newstandard.common.web.mapper.UserMapper;
import com.sinosoft.newstandard.common.web.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements IUserService {

    @Override
    public User getUserByUserName(String username) {
        return getMapper().getUserByUserName(username);
    }

    @Override
    public User getUserWithDetails(String username) {
        return getMapper().getUserWithDetails(username);
    }

}
