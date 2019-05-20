package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.Resource;
import com.sinosoft.newstandard.common.web.mapper.ResourceMapper;
import com.sinosoft.newstandard.common.web.service.IResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, ResourceMapper> implements IResourceService {

    @Override
    public Set<Resource> listByRoleId(Long roleId) {
        return getMapper().listByRoleId(roleId);
    }

    @Override
    public Set<Resource> listByRoleIds(List<Long> roleIds) {
        return getMapper().listByRoleIds(roleIds);
    }

    @Override
    public Set<Resource> listByRoleCodes(List<String> roleCodes) {
        return getMapper().listByRoleCodes(roleCodes);
    }

}
