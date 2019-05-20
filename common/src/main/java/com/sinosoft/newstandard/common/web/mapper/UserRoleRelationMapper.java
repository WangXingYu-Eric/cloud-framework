package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.UserRoleRelation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {
    @Delete({
            "delete from sys_user_role_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_user_role_relation (id, user_id, ",
            "role_id, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{roleId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(UserRoleRelation record);

    @InsertProvider(type = UserRoleRelationSqlProvider.class, method = "insertSelective")
    int insertSelective(UserRoleRelation record);

    @Select({
            "select",
            "id, user_id, role_id, create_time, create_user, modify_time, modify_user",
            "from sys_user_role_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    UserRoleRelation selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserRoleRelationSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRoleRelation record);

    @Update({
            "update sys_user_role_relation",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "role_id = #{roleId,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRoleRelation record);

    @Select({
            "select",
            "id, user_id, role_id, create_time, create_user, modify_time, modify_user",
            "from sys_user_role_relation"
    })
    @Results(id = "userrolerelation", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<UserRoleRelation> selectAll();

    //====================The Following Methods Are Added Manually====================
}