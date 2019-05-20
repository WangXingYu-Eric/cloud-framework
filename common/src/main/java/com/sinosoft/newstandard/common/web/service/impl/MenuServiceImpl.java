package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.Menu;
import com.sinosoft.newstandard.common.web.mapper.MenuMapper;
import com.sinosoft.newstandard.common.web.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements IMenuService {


    @Override
    public Set<Menu> listByRoleId(Long roleId) {
        return getMapper().listByRoleId(roleId);
    }

    @Override
    public Set<Menu> listByRoleIds(List<Long> roleIds) {
        return getMapper().listByRoleIds(roleIds);
    }

    @Override
    public Set<Menu> listByRoleCode(String code) {
        return getMapper().listByRoleCode(code);
    }

    @Override
    public Set<Menu> listByRoleCodes(List<String> codes) {
        return getMapper().listByRoleCodes(codes);
    }

}
