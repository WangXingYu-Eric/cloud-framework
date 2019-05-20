package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.User;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {

    public String insertSelective(User record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_user");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getTenantId() != null) {
            sql.VALUES("tenant_id", "#{tenantId,jdbcType=BIGINT}");
        }

        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId,jdbcType=BIGINT}");
        }

        if (record.getDetailsId() != null) {
            sql.VALUES("details_id", "#{detailsId,jdbcType=BIGINT}");
        }

        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }

        if (record.getLoginTime() != null) {
            sql.VALUES("login_time", "#{loginTime,jdbcType=TIMESTAMP}");
        }

        if (record.getLastLoginTime() != null) {
            sql.VALUES("last_login_time", "#{lastLoginTime,jdbcType=TIMESTAMP}");
        }

        if (record.getFailedCount() != null) {
            sql.VALUES("failed_count", "#{failedCount,jdbcType=INTEGER}");
        }

        if (record.getNameCn() != null) {
            sql.VALUES("name_cn", "#{nameCn,jdbcType=VARCHAR}");
        }

        if (record.getNameEn() != null) {
            sql.VALUES("name_en", "#{nameEn,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }

        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }

        sql.VALUES("enabled", "#{enabled,jdbcType=BIT}");

        sql.VALUES("account_non_expired", "#{accountNonExpired,jdbcType=BIT}");

        sql.VALUES("account_non_locked", "#{accountNonLocked,jdbcType=BIT}");

        sql.VALUES("credentials_non_expired", "#{credentialsNonExpired,jdbcType=BIT}");

        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }

        if (record.getDeleteFlag() != null) {
            sql.VALUES("delete_flag", "#{deleteFlag,jdbcType=BIT}");
        }

        if (record.getSequence() != null) {
            sql.VALUES("sequence", "#{sequence,jdbcType=BIGINT}");
        }

        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getCreateUser() != null) {
            sql.VALUES("create_user", "#{createUser,jdbcType=BIGINT}");
        }

        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }

        if (record.getModifyUser() != null) {
            sql.VALUES("modify_user", "#{modifyUser,jdbcType=BIGINT}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(User record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_user");

        if (record.getTenantId() != null) {
            sql.SET("tenant_id = #{tenantId,jdbcType=BIGINT}");
        }

        if (record.getParentId() != null) {
            sql.SET("parent_id = #{parentId,jdbcType=BIGINT}");
        }

        if (record.getDetailsId() != null) {
            sql.SET("details_id = #{detailsId,jdbcType=BIGINT}");
        }

        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }

        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }

        if (record.getLoginTime() != null) {
            sql.SET("login_time = #{loginTime,jdbcType=TIMESTAMP}");
        }

        if (record.getLastLoginTime() != null) {
            sql.SET("last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP}");
        }

        if (record.getFailedCount() != null) {
            sql.SET("failed_count = #{failedCount,jdbcType=INTEGER}");
        }

        if (record.getNameCn() != null) {
            sql.SET("name_cn = #{nameCn,jdbcType=VARCHAR}");
        }

        if (record.getNameEn() != null) {
            sql.SET("name_en = #{nameEn,jdbcType=VARCHAR}");
        }

        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }

        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }

        sql.SET("enabled = #{enabled,jdbcType=BIT}");

        sql.SET("account_non_expired = #{accountNonExpired,jdbcType=BIT}");

        sql.SET("account_non_locked = #{accountNonLocked,jdbcType=BIT}");

        sql.SET("credentials_non_expired = #{credentialsNonExpired,jdbcType=BIT}");

        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }

        if (record.getDeleteFlag() != null) {
            sql.SET("delete_flag = #{deleteFlag,jdbcType=BIT}");
        }

        if (record.getSequence() != null) {
            sql.SET("sequence = #{sequence,jdbcType=BIGINT}");
        }

        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getCreateUser() != null) {
            sql.SET("create_user = #{createUser,jdbcType=BIGINT}");
        }

        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }

        if (record.getModifyUser() != null) {
            sql.SET("modify_user = #{modifyUser,jdbcType=BIGINT}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }
}