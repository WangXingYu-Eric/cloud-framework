package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.Role;
import com.sinosoft.newstandard.common.web.mapper.RoleMapper;
import com.sinosoft.newstandard.common.web.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements IRoleService {

    @Override
    public Set<Role> listByUserId(Long userId) {
        return getMapper().listByUserId(userId);
    }

    @Override
    public Set<Role> listByGroupId(Long groupId) {
        return getMapper().listByGroupId(groupId);
    }

    @Override
    public Set<Role> listByGroupIds(List<Long> groupIds) {
        return getMapper().listByGroupIds(groupIds);
    }

}
