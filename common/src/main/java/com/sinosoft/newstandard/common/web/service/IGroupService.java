package com.sinosoft.newstandard.common.web.service;

import com.sinosoft.newstandard.common.web.entity.Group;

import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IGroupService extends IBaseService<Group> {

    Set<Group> getGroupsByUserId(Long userId);

}
