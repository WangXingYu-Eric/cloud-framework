package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Tenant;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface TenantMapper extends BaseMapper<Tenant> {
    @Delete({
            "delete from sys_tenant",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_tenant (id, name_cn, ",
            "name_en, description, ",
            "remark, status, ",
            "delete_flag, sequence, ",
            "create_time, create_user, ",
            "modify_time, modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{nameCn,jdbcType=VARCHAR}, ",
            "#{nameEn,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
            "#{remark,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{deleteFlag,jdbcType=BIT}, #{sequence,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=BIGINT}, ",
            "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=BIGINT})"
    })
    int insert(Tenant record);

    @InsertProvider(type = TenantSqlProvider.class, method = "insertSelective")
    int insertSelective(Tenant record);

    @Select({
            "select",
            "id, name_cn, name_en, description, remark, status, delete_flag, sequence, create_time, ",
            "create_user, modify_time, modify_user",
            "from sys_tenant",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
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
    Tenant selectByPrimaryKey(Long id);

    @UpdateProvider(type = TenantSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Tenant record);

    @Update({
            "update sys_tenant",
            "set name_cn = #{nameCn,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(Tenant record);

    @Select({
            "select",
            "id, name_cn, name_en, description, remark, status, delete_flag, sequence, create_time, ",
            "create_user, modify_time, modify_user",
            "from sys_tenant"
    })
    @Results(id = "tenant", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
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
    List<Tenant> selectAll();

    //====================The Following Methods Are Added Manually====================
}