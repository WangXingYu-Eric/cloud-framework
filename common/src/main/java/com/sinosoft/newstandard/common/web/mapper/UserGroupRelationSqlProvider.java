package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.UserGroupRelation;
import org.apache.ibatis.jdbc.SQL;

public class UserGroupRelationSqlProvider {

    public String insertSelective(UserGroupRelation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_user_group_relation");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }

        if (record.getGroupId() != null) {
            sql.VALUES("group_id", "#{groupId,jdbcType=BIGINT}");
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

    public String updateByPrimaryKeySelective(UserGroupRelation record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_user_group_relation");

        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=BIGINT}");
        }

        if (record.getGroupId() != null) {
            sql.SET("group_id = #{groupId,jdbcType=BIGINT}");
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