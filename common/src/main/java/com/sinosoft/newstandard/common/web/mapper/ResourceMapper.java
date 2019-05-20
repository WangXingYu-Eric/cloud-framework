package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Resource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Set;

public interface ResourceMapper extends BaseMapper<Resource> {
    @Delete({
            "delete from sys_resource",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_resource (id, code, ",
            "name, url, method, ",
            "description, remark, ",
            "status, delete_flag, ",
            "sequence, create_time, ",
            "create_user, modify_time, ",
            "modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{code,jdbcType=VARCHAR}, ",
            "#{name,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{method,jdbcType=VARCHAR}, ",
            "#{description,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=INTEGER}, #{deleteFlag,jdbcType=BIT}, ",
            "#{sequence,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{createUser,jdbcType=BIGINT}, #{modifyTime,jdbcType=TIMESTAMP}, ",
            "#{modifyUser,jdbcType=BIGINT})"
    })
    int insert(Resource record);

    @InsertProvider(type = ResourceSqlProvider.class, method = "insertSelective")
    int insertSelective(Resource record);

    @Select({
            "select",
            "id, code, name, url, method, description, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_resource",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method", property = "method", jdbcType = JdbcType.VARCHAR),
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
    Resource selectByPrimaryKey(Long id);

    @UpdateProvider(type = ResourceSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Resource record);

    @Update({
            "update sys_resource",
            "set code = #{code,jdbcType=VARCHAR},",
            "name = #{name,jdbcType=VARCHAR},",
            "url = #{url,jdbcType=VARCHAR},",
            "method = #{method,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(Resource record);

    @Select({
            "select",
            "id, code, name, url, method, description, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_resource"
    })
    @Results(id = "resource", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method", property = "method", jdbcType = JdbcType.VARCHAR),
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
    List<Resource> selectAll();

    //====================The Following Methods Are Added Manually====================

    @Select({
            "select",
            "r.id, r.code, r.name, r.url, r.method, r.description, r.remark, r.status, r.delete_flag, r.sequence, ",
            "r.create_time, r.create_user, r.modify_time, r.modify_user",
            "from sys_resource r",
            "inner join sys_role_resource_relation rrr on r.id = rrr.resource_id",
            "where rrr.role_id = #{roleId}"
    })
    @ResultMap("resource")
    Set<Resource> listByRoleId(@Param("roleId") Long roleId);

    @Select({
            "<script>",
            "select",
            "r.id, r.code, r.name, r.url, r.method, r.description, r.remark, r.status, r.delete_flag, r.sequence, ",
            "r.create_time, r.create_user, r.modify_time, r.modify_user",
            "from sys_resource r",
            "inner join sys_role_resource_relation rrr on r.id = rrr.resource_id",
            "<where>",
            "   <if test='roleIds != null and roleIds.size() > 0'>",
            "       and rrr.role_id in",
            "       <foreach  collection='roleIds' item='roleId' open='('  separator=','  close=')'>#{roleId}</foreach>",
            "   </if>",
            "</where>",
            "</script>"
    })
    @ResultMap("resource")
    Set<Resource> listByRoleIds(@Param("roleIds") List<Long> roleIds);

    @Select({
            "<script>",
            "select",
            "resource.id, resource.code, resource.name, resource.url, resource.method, resource.description, resource.remark, resource.status, resource.delete_flag, resource.sequence, ",
            "resource.create_time, resource.create_user, resource.modify_time, resource.modify_user",
            "from sys_resource resource",
            "inner join sys_role_resource_relation rrr on resource.id = rrr.resource_id",
            "inner join sys_role r on r.id = rrr.role_id",
            "<where>",
            "   r.code in",
            "   <if test='roleCodes.size() > 0'>",
            "       <foreach collection='roleCodes' index='index' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "   </if>",
            "</where>",
            "</script>"
    })
    @ResultMap("resource")
    Set<Resource> listByRoleCodes(@Param("roleCodes") List<String> roleCodes);

}