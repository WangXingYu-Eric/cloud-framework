package com.sinosoft.newstandard.common.web.service;

import com.sinosoft.newstandard.common.web.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IResourceService extends IBaseService<Resource> {

    Set<Resource> listByRoleId(Long roleId);

    Set<Resource> listByRoleIds(List<Long> roleIds);

    Set<Resource> listByRoleCodes(List<String> roleCodes);

}
