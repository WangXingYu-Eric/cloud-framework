package com.sinosoft.newstandard.securityservice.web.service;


import com.sinosoft.newstandard.common.web.entity.Group;
import com.sinosoft.newstandard.common.web.entity.Role;
import com.sinosoft.newstandard.common.web.entity.User;
import com.sinosoft.newstandard.common.web.service.IGroupService;
import com.sinosoft.newstandard.common.web.service.IRoleService;
import com.sinosoft.newstandard.common.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在.", null);
        }
        logger.info("查询用户:{}", user.getUsername());
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                this.obtainGrantedAuthorities(user));
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user 用户
     * @return 角色集合
     */
    private Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
        Set<Role> roles = roleService.listByUserId(user.getId());
        Set<Group> groups = groupService.getGroupsByUserId(user.getId());
        Set<Role> rolesByGroupIds = roleService.listByGroupIds(groups.stream().map(Group::getId).collect(Collectors.toList()));
        roles.addAll(rolesByGroupIds);
        logger.info("用户名:{},拥有的角色:{}", user.getUsername(), roles.stream().map(Role::getCode).collect(Collectors.toSet()));
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toSet());
    }

}
