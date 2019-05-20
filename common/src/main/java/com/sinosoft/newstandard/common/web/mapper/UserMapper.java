package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Delete({
            "delete from sys_user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_user (id, tenant_id, ",
            "parent_id, details_id, ",
            "username, password, ",
            "login_time, last_login_time, ",
            "failed_count, name_cn, ",
            "name_en, email, phone, ",
            "enabled, account_non_expired, ",
            "account_non_locked, credentials_non_expired, ",
            "remark, status, ",
            "delete_flag, sequence, ",
            "create_time, create_user, ",
            "modify_time, modify_user)",
            "values (#{id,jdbcType=BIGINT}, #{tenantId,jdbcType=BIGINT}, ",
            "#{parentId,jdbcType=BIGINT}, #{detailsId,jdbcType=BIGINT}, ",
            "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
            "#{loginTime,jdbcType=TIMESTAMP}, #{lastLoginTime,jdbcType=TIMESTAMP}, ",
            "#{failedCount,jdbcType=INTEGER}, #{nameCn,jdbcType=VARCHAR}, ",
            "#{nameEn,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
            "#{enabled,jdbcType=BIT}, #{accountNonExpired,jdbcType=BIT}, ",
            "#{accountNonLocked,jdbcType=BIT}, #{credentialsNonExpired,jdbcType=BIT}, ",
            "#{remark,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
            "#{deleteFlag,jdbcType=BIT}, #{sequence,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=BIGINT}, ",
            "#{modifyTime,jdbcType=TIMESTAMP}, #{modifyUser,jdbcType=BIGINT})"
    })
    int insert(User record);

    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    int insertSelective(User record);

    @Select({
            "select",
            "id, tenant_id, parent_id, details_id, username, password, login_time, last_login_time, ",
            "failed_count, name_cn, name_en, email, phone, enabled, account_non_expired, ",
            "account_non_locked, credentials_non_expired, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "details_id", property = "detailsId", jdbcType = JdbcType.BIGINT),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_time", property = "loginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_time", property = "lastLoginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "failed_count", property = "failedCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.BIT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "sequence", property = "sequence", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    User selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
            "update sys_user",
            "set tenant_id = #{tenantId,jdbcType=BIGINT},",
            "parent_id = #{parentId,jdbcType=BIGINT},",
            "details_id = #{detailsId,jdbcType=BIGINT},",
            "username = #{username,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "login_time = #{loginTime,jdbcType=TIMESTAMP},",
            "last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP},",
            "failed_count = #{failedCount,jdbcType=INTEGER},",
            "name_cn = #{nameCn,jdbcType=VARCHAR},",
            "name_en = #{nameEn,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "phone = #{phone,jdbcType=VARCHAR},",
            "enabled = #{enabled,jdbcType=BIT},",
            "account_non_expired = #{accountNonExpired,jdbcType=BIT},",
            "account_non_locked = #{accountNonLocked,jdbcType=BIT},",
            "credentials_non_expired = #{credentialsNonExpired,jdbcType=BIT},",
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
    int updateByPrimaryKey(User record);

    @Select({
            "select",
            "id, tenant_id, parent_id, details_id, username, password, login_time, last_login_time, ",
            "failed_count, name_cn, name_en, email, phone, enabled, account_non_expired, ",
            "account_non_locked, credentials_non_expired, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_user"
    })
    @Results(id = "user", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "details_id", property = "detailsId", jdbcType = JdbcType.BIGINT),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_time", property = "loginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_time", property = "lastLoginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "failed_count", property = "failedCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.BIT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "sequence", property = "sequence", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT)
    })
    List<User> selectAll();

    //====================The Following Methods Are Added Manually====================

    @Select({
            "select",
            "id, tenant_id, parent_id, details_id, username, password, login_time, last_login_time, ",
            "failed_count, name_cn, name_en, email, phone, enabled, account_non_expired, ",
            "account_non_locked, credentials_non_expired, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_user",
            "where username = #{username}"
    })
    @ResultMap("user")
    User getUserByUserName(@Param("username") String username);

    @Select({
            "select",
            "id, tenant_id, parent_id, details_id, username, password, login_time, last_login_time, ",
            "failed_count, name_cn, name_en, email, phone, enabled, account_non_expired, ",
            "account_non_locked, credentials_non_expired, remark, status, delete_flag, sequence, ",
            "create_time, create_user, modify_time, modify_user",
            "from sys_user",
            "where username = #{username}"
    })
    @Results(id = "userWithDetails", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "details_id", property = "detailsId", jdbcType = JdbcType.BIGINT),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_time", property = "loginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_time", property = "lastLoginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "failed_count", property = "failedCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "name_cn", property = "nameCn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name_en", property = "nameEn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.BIT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "sequence", property = "sequence", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user", property = "createUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "modify_time", property = "modifyTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "modify_user", property = "modifyUser", jdbcType = JdbcType.BIGINT),
            @Result(column = "details_id", property = "userDetails", jdbcType = JdbcType.BIGINT, one = @One(select = "com.sinosoft.newstandard.common.web.mapper.UserDetailsMapper.selectByPrimaryKey", fetchType = FetchType.LAZY))
    })
    User getUserWithDetails(@Param("username") String username);

}