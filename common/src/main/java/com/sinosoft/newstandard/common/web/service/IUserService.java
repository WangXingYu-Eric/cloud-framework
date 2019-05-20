package com.sinosoft.newstandard.common.web.service;

import com.sinosoft.newstandard.common.web.entity.User;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IUserService extends IBaseService<User> {

    User getUserByUserName(String username);

    User getUserWithDetails(String username);

}
