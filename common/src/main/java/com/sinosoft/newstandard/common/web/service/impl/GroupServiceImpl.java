package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.entity.Group;
import com.sinosoft.newstandard.common.web.mapper.GroupMapper;
import com.sinosoft.newstandard.common.web.service.IGroupService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, GroupMapper> implements IGroupService {

    @Override
    public Set<Group> getGroupsByUserId(Long userId) {
        return getMapper().listByUserId(userId);
    }

}
