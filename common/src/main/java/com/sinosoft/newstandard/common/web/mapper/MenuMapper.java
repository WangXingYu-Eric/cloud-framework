package com.sinosoft.newstandard.common.web.mapper;


import com.sinosoft.newstandard.common.web.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Set;

public interface MenuMapper extends BaseMapper<Menu> {
    @Delete({
            "delete from sys_menu",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_menu (id, tenant_id, ",
            "parent_id, type, name_cn, ",
            "name_en, url, method, ",
            "level, icon, description, ",
            "remark, status, ",
            "delete_flag, sequence, ",
            "create_time, create_user, ",
            "modify_time, modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{tenantId,jdbcType=BIGINT}, ",
            "#{parentId,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, #{nameCn,jdbcType=VARCHAR}, ",
            "#{nameEn,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{method,jdbcType=VARCHAR}, ",
            "#{level,jdbcType=INTEGER}, #{icon,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
            "#{remark,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{deleteFlag,jdbcType=BIT}, #{sequence,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=BIGINT}, ",
            "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=BIGINT})"
    })
    int insert(Menu record);

    @InsertProvider(type = MenuSqlProvider.class, method = "insertSelective")
    int insertSelective(Menu record);

    @Select({
            "select",
            "id, tenant_id, parent_id, type, name_cn, name_en, url, method, level, icon, ",
            "description, remark, status, delete_flag, sequence, create_time, create_user, ",
            "modify_time, modify_user",
            "from sys_menu",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method", property = "method", jdbcType = JdbcType.VARCHAR),
            @Result(column = "level", property = "level", jdbcType = JdbcType.INTEGER),
            @Result(column = "icon", property = "icon", jdbcType = JdbcType.VARCHAR),
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
    Menu selectByPrimaryKey(Long id);

    @UpdateProvider(type = MenuSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Menu record);

    @Update({
            "update sys_menu",
            "set tenant_id = #{tenantId,jdbcType=BIGINT},",
            "parent_id = #{parentId,jdbcType=BIGINT},",
            "type = #{type,jdbcType=INTEGER},",
            "name_cn = #{nameCn,jdbcType=VARCHAR},",
            "name_en = #{nameEn,jdbcType=VARCHAR},",
            "url = #{url,jdbcType=VARCHAR},",
            "method = #{method,jdbcType=VARCHAR},",
            "level = #{level,jdbcType=INTEGER},",
            "icon = #{icon,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(Menu record);

    @Select({
            "select",
            "id, tenant_id, parent_id, type, name_cn, name_en, url, method, level, icon, ",
            "description, remark, status, delete_flag, sequence, create_time, create_user, ",
            "modify_time, modify_user",
            "from sys_menu"
    })
    @Results(id = "menu", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method", property = "method", jdbcType = JdbcType.VARCHAR),
            @Result(column = "level", property = "level", jdbcType = JdbcType.INTEGER),
            @Result(column = "icon", property = "icon", jdbcType = JdbcType.VARCHAR),
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
    List<Menu> selectAll();

    //====================The Following Methods Are Added Manually====================

    @Select({
            "select",
            "m.id, m.tenant_id, m.parent_id, m.type, m.name_cn, m.name_en, m.url, m.method, m.level, m.icon, ",
            "m.description, m.remark, m.status, m.delete_flag, m.sequence, m.create_time, m.create_user, ",
            "m.modify_time, m.modify_user",
            "from sys_menu m",
            "inner join sys_role_menu_relation rmr on m.id = rmr.menu_id",
            "where rmr.role_id = #{roleId}"
    })
    @ResultMap("menu")
    Set<Menu> listByRoleId(@Param("roleId") Long roleId);

    @Select({
            "<script>",
            "select",
            "m.id, m.tenant_id, m.parent_id, m.type, m.name_cn, m.name_en, m.url, m.method, m.level, m.icon, ",
            "m.description, m.remark, m.status, m.delete_flag, m.sequence, m.create_time, m.create_user, ",
            "m.modify_time, m.modify_user",
            "from sys_menu m",
            "inner join sys_role_menu_relation rmr on m.id = rmr.menu_id",
            "<where>",
            "   <if test='roleIds != null and roleIds.size() > 0'>",
            "       and rmr.role_id in",
            "       <foreach  collection='roleIds' item='roleId' open='('  separator=','  close=')'>#{roleId}</foreach>",
            "   </if>",
            "</where>",
            "</script>"
    })
    @ResultMap("menu")
    Set<Menu> listByRoleIds(@Param("roleIds") List<Long> roleIds);

    @Select({
            "select",
            "m.id, m.tenant_id, m.parent_id, m.type, m.name_cn, m.name_en, m.url, m.method, m.level, m.icon, ",
            "m.description, m.remark, m.status, m.delete_flag, m.sequence, m.create_time, m.create_user, ",
            "m.modify_time, m.modify_user",
            "from sys_menu m",
            "inner join sys_role_menu_relation rmr on m.id = rmr.menu_id",
            "inner join sys_role r on rmr.role_Id = r.id",
            "where r.code = #{code}"
    })
    @ResultMap("menu")
    Set<Menu> listByRoleCode(@Param("code") String code);

    @Select({
            "<script>",
            "select",
            "m.id, m.tenant_id, m.parent_id, m.type, m.name_cn, m.name_en, m.url, m.method, m.level, m.icon, ",
            "m.description, m.remark, m.status, m.delete_flag, m.sequence, m.create_time, m.create_user, ",
            "m.modify_time, m.modify_user",
            "from sys_menu m",
            "inner join sys_role_menu_relation rmr on m.id = rmr.menu_id",
            "inner join sys_role r on rmr.role_Id = r.id",
            "<where>",
            "   <if test='codes != null and codes.size() > 0'>",
            "       and r.code in",
            "       <foreach  collection='codes' item='code' open='('  separator=','  close=')'>#{code}</foreach>",
            "   </if>",
            "</where>",
            "</script>"
    })
    @ResultMap("menu")
    Set<Menu> listByRoleCodes(@Param("codes") List<String> codes);
}