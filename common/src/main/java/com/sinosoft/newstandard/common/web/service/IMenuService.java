package com.sinosoft.newstandard.common.web.service;

import com.sinosoft.newstandard.common.web.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface IMenuService extends IBaseService<Menu> {

    Set<Menu> listByRoleId(Long roleId);

    Set<Menu> listByRoleIds(List<Long> roleIds);

    Set<Menu> listByRoleCode(String code);

    Set<Menu> listByRoleCodes(List<String> codes);

}
