package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Group;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Set;

public interface GroupMapper extends BaseMapper<Group> {
    @Delete({
            "delete from sys_group",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_group (id, tenant_id, ",
            "parent_id, type, name_cn, ",
            "name_en, description, ",
            "remark, status, ",
            "delete_flag, sequence, ",
            "create_time, create_user, ",
            "modify_time, modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{tenantId,jdbcType=BIGINT}, ",
            "#{parentId,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, #{nameCn,jdbcType=VARCHAR}, ",
            "#{nameEn,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
            "#{remark,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{deleteFlag,jdbcType=BIT}, #{sequence,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=BIGINT}, ",
            "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=BIGINT})"
    })
    int insert(Group record);

    @InsertProvider(type = GroupSqlProvider.class, method = "insertSelective")
    int insertSelective(Group record);

    @Select({
            "select",
            "id, tenant_id, parent_id, type, name_cn, name_en, description, remark, status, ",
            "delete_flag, sequence, create_time, create_user, modify_time, modify_user",
            "from sys_group",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
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
    Group selectByPrimaryKey(Long id);

    @UpdateProvider(type = GroupSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Group record);

    @Update({
            "update sys_group",
            "set tenant_id = #{tenantId,jdbcType=BIGINT},",
            "parent_id = #{parentId,jdbcType=BIGINT},",
            "type = #{type,jdbcType=INTEGER},",
            "name_cn = #{nameCn,jdbcType=VARCHAR},",
            "name_en = #{nameEn,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(Group record);

    @Select({
            "select",
            "id, tenant_id, parent_id, type, name_cn, name_en, description, remark, status, ",
            "delete_flag, sequence, create_time, create_user, modify_time, modify_user",
            "from sys_group"
    })
    @Results(id = "group", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
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
    List<Group> selectAll();

    //====================The Following Methods Are Added Manually====================

    @Select({
            "select",
            "g.id, g.tenant_id, g.parent_id, g.type, g.name_cn, g.name_en, g.description, g.remark, g.status, ",
            "g.delete_flag, g.sequence, g.create_time, g.create_user, g.modify_time, g.modify_user",
            "from sys_group g",
            "inner join sys_user_group_relation ugr on g.id = ugr.group_id",
            "where ugr.user_id = #{userId}"
    })
    @ResultMap("group")
    Set<Group> listByUserId(@Param("userId") Long userId);
}