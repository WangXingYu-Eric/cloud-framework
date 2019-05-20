package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Resource;
import org.apache.ibatis.jdbc.SQL;

public class ResourceSqlProvider {

    public String insertSelective(Resource record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_resource");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }

        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }

        if (record.getMethod() != null) {
            sql.VALUES("method", "#{method,jdbcType=VARCHAR}");
        }

        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }

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

    public String updateByPrimaryKeySelective(Resource record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_resource");

        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }

        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.SET("url = #{url,jdbcType=VARCHAR}");
        }

        if (record.getMethod() != null) {
            sql.SET("method = #{method,jdbcType=VARCHAR}");
        }

        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }

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