package com.sinosoft.newstandard.common.web.mapper;

import java.util.List;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public interface BaseMapper<Entity> {

    int deleteByPrimaryKey(Long id);

    int insert(Entity record);

    int insertSelective(Entity record);

    Entity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Entity record);

    int updateByPrimaryKey(Entity record);

    List<Entity> selectAll();

}
