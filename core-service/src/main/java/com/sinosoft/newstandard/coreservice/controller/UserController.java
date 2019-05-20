package com.sinosoft.newstandard.coreservice.controller;


import com.sinosoft.newstandard.common.web.controller.BaseController;
import com.sinosoft.newstandard.common.web.entity.Menu;
import com.sinosoft.newstandard.common.web.entity.Result;
import com.sinosoft.newstandard.common.web.service.IMenuService;
import com.sinosoft.newstandard.coreservice.service.CoreIUserService;
import com.sinosoft.newstandard.coreservice.service.impl.BaseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private BaseUserServiceImpl baseUserServiceImpl;

    @Autowired
    @Qualifier("otherUserServiceImpl")
    private CoreIUserService otherUserServiceImpl;

    @Autowired
    private IMenuService menuService;

    @PostMapping
    public Result test() throws InterruptedException {
        Set<Menu> menus = menuService.listByRoleCodes(getRoleCodes());
        return Result.success(menus);
    }

}
