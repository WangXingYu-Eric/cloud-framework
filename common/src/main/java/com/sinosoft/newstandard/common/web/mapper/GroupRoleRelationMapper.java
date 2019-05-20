package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.GroupRoleRelation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface GroupRoleRelationMapper extends BaseMapper<GroupRoleRelation> {
    @Delete({
            "delete from sys_group_role_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_group_role_relation (id, group_id, ",
            "role_id, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{groupId,jdbcType=BIGINT}, ",
            "#{roleId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(GroupRoleRelation record);

    @InsertProvider(type = GroupRoleRelationSqlProvider.class, method = "insertSelective")
    int insertSelective(GroupRoleRelation record);

    @Select({
            "select",
            "id, group_id, role_id, create_time, create_user, modify_time, modify_user",
            "from sys_group_role_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    GroupRoleRelation selectByPrimaryKey(Long id);

    @UpdateProvider(type = GroupRoleRelationSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(GroupRoleRelation record);

    @Update({
            "update sys_group_role_relation",
            "set group_id = #{groupId,jdbcType=BIGINT},",
            "role_id = #{roleId,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(GroupRoleRelation record);

    @Select({
            "select",
            "id, group_id, role_id, create_time, create_user, modify_time, modify_user",
            "from sys_group_role_relation"
    })
    @Results(id = "grouprolerelation", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<GroupRoleRelation> selectAll();

    //====================The Following Methods Are Added Manually====================
}