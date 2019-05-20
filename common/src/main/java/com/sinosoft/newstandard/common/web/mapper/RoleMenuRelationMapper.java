package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.RoleMenuRelation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RoleMenuRelationMapper extends BaseMapper<RoleMenuRelation> {
    @Delete({
            "delete from sys_role_menu_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_role_menu_relation (id, role_id, ",
            "menu_id, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
            "#{menuId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(RoleMenuRelation record);

    @InsertProvider(type = RoleMenuRelationSqlProvider.class, method = "insertSelective")
    int insertSelective(RoleMenuRelation record);

    @Select({
            "select",
            "id, role_id, menu_id, create_time, create_user, modify_time, modify_user",
            "from sys_role_menu_relation",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    RoleMenuRelation selectByPrimaryKey(Long id);

    @UpdateProvider(type = RoleMenuRelationSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RoleMenuRelation record);

    @Update({
            "update sys_role_menu_relation",
            "set role_id = #{roleId,jdbcType=BIGINT},",
            "menu_id = #{menuId,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleMenuRelation record);

    @Select({
            "select",
            "id, role_id, menu_id, create_time, create_user, modify_time, modify_user",
            "from sys_role_menu_relation"
    })
    @Results(id = "rolemenurelation", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<RoleMenuRelation> selectAll();

    //====================The Following Methods Are Added Manually====================
}