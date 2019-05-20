package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.UserGroupRelation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserGroupRelationMapper extends BaseMapper<UserGroupRelation> {
    @Delete({
            "delete from sys_user_group_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_user_group_relation (id, user_id, ",
            "group_id, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
            "#{groupId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(UserGroupRelation record);

    @InsertProvider(type = UserGroupRelationSqlProvider.class, method = "insertSelective")
    int insertSelective(UserGroupRelation record);

    @Select({
            "select",
            "id, user_id, group_id, create_time, create_user, modify_time, modify_user",
            "from sys_user_group_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    UserGroupRelation selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserGroupRelationSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserGroupRelation record);

    @Update({
            "update sys_user_group_relation",
            "set user_id = #{userId,jdbcType=BIGINT},",
            "group_id = #{groupId,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserGroupRelation record);

    @Select({
            "select",
            "id, user_id, group_id, create_time, create_user, modify_time, modify_user",
            "from sys_user_group_relation"
    })
    @Results(id = "usergrouprelation", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "group_id", property = "groupId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<UserGroupRelation> selectAll();

    //====================The Following Methods Are Added Manually====================
}