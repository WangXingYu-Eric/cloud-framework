package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {
    @Delete({
            "delete from sys_role",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_role (id, tenant_id, ",
            "code, name, description, ",
            "remark, status, ",
            "delete_flag, sequence, ",
            "create_time, create_user, ",
            "modify_time, modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{tenantId,jdbcType=BIGINT}, ",
            "#{code,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
            "#{remark,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{deleteFlag,jdbcType=BIT}, #{sequence,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=BIGINT}, ",
            "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=BIGINT})"
    })
    int insert(Role record);

    @InsertProvider(type = RoleSqlProvider.class, method = "insertSelective")
    int insertSelective(Role record);

    @Select({
            "select",
            "id, tenant_id, code, name, description, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_role",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "sequence", property = "sequence", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    Role selectByPrimaryKey(Long id);

    @UpdateProvider(type = RoleSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Role record);

    @Update({
            "update sys_role",
            "set tenant_id = #{tenantId,jdbcType=BIGINT},",
            "code = #{code,jdbcType=VARCHAR},",
            "name = #{name,jdbcType=VARCHAR},",
            "description = #{description,jdbcType=VARCHAR},",
            "remark = #{remark,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=INTEGER},",
            "delete_flag = #{deleteFlag,jdbcType=BIT},",
            "sequence = #{sequence,jdbcType=BIGINT},",
            "create_time = #{createTime,jdbcType=TIMESTAMP},",
            "create_user = #{createUser,jdbcType=BIGINT},",
            "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
            "modify_user = #{modifyUser,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Role record);

    @Select({
            "select",
            "id, tenant_id, code, name, description, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_role"
    })
    @Results(id = "role", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "sequence", property = "sequence", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<Role> selectAll();

    //====================The Following Methods Are Added Manually====================

    @Select({
            "select",
            "r.id, r.tenant_id, r.code, r.name, r.description, r.remark, r.status, r.delete_flag, r.sequence, r.create_time, r.create_user, r.modify_time, r.modify_user",
            "from sys_role r",
            "inner join sys_user_role_relation urr on r.id = urr.role_id",
            "where urr.user_id = #{userId}"
    })
    @ResultMap("role")
    Set<Role> listByUserId(@Param("userId") Long userId);


    @Select({
            "select",
            "r.id, r.tenant_id, r.code, r.name, r.description, r.remark, r.status, r.delete_flag, r.sequence, r.create_time, r.create_user, r.modify_time, r.modify_user",
            "from sys_role r",
            "inner join sys_group_role_relation grr on r.id = grr.role_id",
            "where grr.group_id = #{groupId}"
    })
    @ResultMap("role")
    Set<Role> listByGroupId(@Param("groupId") Long groupId);

    @Select({
            "<script>",
            "select",
            "r.id, r.tenant_id, r.code, r.name, r.description, r.remark, r.status, r.delete_flag, r.sequence, r.create_time, r.create_user, r.modify_time, r.modify_user",
            "from sys_role r",
            "inner join sys_group_role_relation grr on r.id = grr.role_id",
            "<where>",
            "   <if test='groupIds != null and groupIds.size() > 0'>",
            "       and grr.group_id in",
            "       <foreach  collection='groupIds' item='groupId' open='('  separator=','  close=')'>#{groupId}</foreach>",
            "   </if>",
            "</where>",
            "</script>"
    })
    @ResultMap("role")
    Set<Role> listByGroupIds(@Param("groupIds") List<Long> groupIds);
}