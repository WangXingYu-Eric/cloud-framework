package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.GroupRoleRelation;
import org.apache.ibatis.jdbc.SQL;

public class GroupRoleRelationSqlProvider {

    public String insertSelective(GroupRoleRelation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_group_role_relation");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getGroupId() != null) {
            sql.VALUES("group_id", "#{groupId,jdbcType=BIGINT}");
        }

        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=BIGINT}");
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

    public String updateByPrimaryKeySelective(GroupRoleRelation record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_group_role_relation");

        if (record.getGroupId() != null) {
            sql.SET("group_id = #{groupId,jdbcType=BIGINT}");
        }

        if (record.getRoleId() != null) {
            sql.SET("role_id = #{roleId,jdbcType=BIGINT}");
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