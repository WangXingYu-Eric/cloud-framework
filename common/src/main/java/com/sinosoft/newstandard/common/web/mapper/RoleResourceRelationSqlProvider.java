package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.RoleResourceRelation;
import org.apache.ibatis.jdbc.SQL;

public class RoleResourceRelationSqlProvider {

    public String insertSelective(RoleResourceRelation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_role_resource_relation");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=BIGINT}");
        }

        if (record.getResourceId() != null) {
            sql.VALUES("resource_id", "#{resourceId,jdbcType=BIGINT}");
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

    public String updateByPrimaryKeySelective(RoleResourceRelation record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_role_resource_relation");

        if (record.getRoleId() != null) {
            sql.SET("role_id = #{roleId,jdbcType=BIGINT}");
        }

        if (record.getResourceId() != null) {
            sql.SET("resource_id = #{resourceId,jdbcType=BIGINT}");
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