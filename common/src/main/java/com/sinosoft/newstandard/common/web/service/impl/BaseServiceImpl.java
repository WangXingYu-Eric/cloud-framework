package com.sinosoft.newstandard.common.web.service.impl;

import com.sinosoft.newstandard.common.web.mapper.BaseMapper;
import com.sinosoft.newstandard.common.web.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class BaseServiceImpl<Entity, Mapper extends BaseMapper<Entity>> implements IBaseService<Entity> {

    @Autowired
    private Mapper mapper;

    public Mapper getMapper() {
        return mapper;
    }

    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int insert(Entity record) {
        return mapper.insert(record);
    }

    public int insertSelective(Entity record) {
        return mapper.insertSelective(record);
    }

    public Entity selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Entity record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Entity record) {
        return mapper.updateByPrimaryKey(record);
    }

    public List<Entity> selectAll() {
        return mapper.selectAll();
    }

}
