package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.Menu;
import org.apache.ibatis.jdbc.SQL;

public class MenuSqlProvider {

    public String insertSelective(Menu record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_menu");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getTenantId() != null) {
            sql.VALUES("tenant_id", "#{tenantId,jdbcType=BIGINT}");
        }

        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId,jdbcType=BIGINT}");
        }

        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }

        if (record.getNameCn() != null) {
            sql.VALUES("name_cn", "#{nameCn,jdbcType=VARCHAR}");
        }

        if (record.getNameEn() != null) {
            sql.VALUES("name_en", "#{nameEn,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.VALUES("url", "#{url,jdbcType=VARCHAR}");
        }

        if (record.getMethod() != null) {
            sql.VALUES("method", "#{method,jdbcType=VARCHAR}");
        }

        if (record.getLevel() != null) {
            sql.VALUES("level", "#{level,jdbcType=INTEGER}");
        }

        if (record.getIcon() != null) {
            sql.VALUES("icon", "#{icon,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(Menu record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_menu");

        if (record.getTenantId() != null) {
            sql.SET("tenant_id = #{tenantId,jdbcType=BIGINT}");
        }

        if (record.getParentId() != null) {
            sql.SET("parent_id = #{parentId,jdbcType=BIGINT}");
        }

        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }

        if (record.getNameCn() != null) {
            sql.SET("name_cn = #{nameCn,jdbcType=VARCHAR}");
        }

        if (record.getNameEn() != null) {
            sql.SET("name_en = #{nameEn,jdbcType=VARCHAR}");
        }

        if (record.getUrl() != null) {
            sql.SET("url = #{url,jdbcType=VARCHAR}");
        }

        if (record.getMethod() != null) {
            sql.SET("method = #{method,jdbcType=VARCHAR}");
        }

        if (record.getLevel() != null) {
            sql.SET("level = #{level,jdbcType=INTEGER}");
        }

        if (record.getIcon() != null) {
            sql.SET("icon = #{icon,jdbcType=VARCHAR}");
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