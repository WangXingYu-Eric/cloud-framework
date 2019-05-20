package com.sinosoft.newstandard.common.web.service;

import com.sinosoft.newstandard.common.web.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IRoleService extends IBaseService<Role> {

    Set<Role> listByUserId(Long userId);

    Set<Role> listByGroupId(Long groupId);

    Set<Role> listByGroupIds(List<Long> groupIds);

}
