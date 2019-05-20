package com.sinosoft.newstandard.common.web.mapper;


import com.sinosoft.newstandard.common.web.entity.RoleResourceRelation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {
    @Delete({
            "delete from sys_role_resource_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_role_resource_relation (id, role_id, ",
            "resource_id, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
            "#{resourceId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(RoleResourceRelation record);

    @InsertProvider(type = RoleResourceRelationSqlProvider.class, method = "insertSelective")
    int insertSelective(RoleResourceRelation record);

    @Select({
            "select",
            "id, role_id, resource_id, create_time, create_user, modify_time, modify_user",
            "from sys_role_resource_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "resource_id", property = "resourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    RoleResourceRelation selectByPrimaryKey(Long id);

    @UpdateProvider(type = RoleResourceRelationSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RoleResourceRelation record);

    @Update({
            "update sys_role_resource_relation",
            "set role_id = #{roleId,jdbcType=BIGINT},",
            "resource_id = #{resourceId,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleResourceRelation record);

    @Select({
            "select",
            "id, role_id, resource_id, create_time, create_user, modify_time, modify_user",
            "from sys_role_resource_relation"
    })
    @Results(id = "roleresourcerelation", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "resource_id", property = "resourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<RoleResourceRelation> selectAll();

    //====================The Following Methods Are Added Manually====================
}