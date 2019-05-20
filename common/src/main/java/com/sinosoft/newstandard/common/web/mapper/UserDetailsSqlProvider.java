package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.UserDetails;
import org.apache.ibatis.jdbc.SQL;

public class UserDetailsSqlProvider {

    public String insertSelective(UserDetails record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_user_details");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getTenantId() != null) {
            sql.VALUES("tenant_id", "#{tenantId,jdbcType=BIGINT}");
        }

        if (record.getMajorId() != null) {
            sql.VALUES("major_id", "#{majorId,jdbcType=BIGINT}");
        }

        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=BIT}");
        }

        if (record.getBirthday() != null) {
            sql.VALUES("birthday", "#{birthday,jdbcType=VARCHAR}");
        }

        if (record.getEntryDate() != null) {
            sql.VALUES("entry_date", "#{entryDate,jdbcType=VARCHAR}");
        }

        if (record.getWorkCode() != null) {
            sql.VALUES("work_code", "#{workCode,jdbcType=VARCHAR}");
        }

        if (record.getOperatingPost() != null) {
            sql.VALUES("operating_post", "#{operatingPost,jdbcType=INTEGER}");
        }

        if (record.getExtend1() != null) {
            sql.VALUES("extend_1", "#{extend1,jdbcType=VARCHAR}");
        }

        if (record.getExtend2() != null) {
            sql.VALUES("extend_2", "#{extend2,jdbcType=VARCHAR}");
        }

        if (record.getExtend3() != null) {
            sql.VALUES("extend_3", "#{extend3,jdbcType=VARCHAR}");
        }

        if (record.getExtend4() != null) {
            sql.VALUES("extend_4", "#{extend4,jdbcType=VARCHAR}");
        }

        if (record.getExtend5() != null) {
            sql.VALUES("extend_5", "#{extend5,jdbcType=VARCHAR}");
        }

        if (record.getExtend6() != null) {
            sql.VALUES("extend_6", "#{extend6,jdbcType=VARCHAR}");
        }

        if (record.getExtend7() != null) {
            sql.VALUES("extend_7", "#{extend7,jdbcType=VARCHAR}");
        }

        if (record.getExtend8() != null) {
            sql.VALUES("extend_8", "#{extend8,jdbcType=VARCHAR}");
        }

        if (record.getExtend9() != null) {
            sql.VALUES("extend_9", "#{extend9,jdbcType=VARCHAR}");
        }

        if (record.getExtend10() != null) {
            sql.VALUES("extend_10", "#{extend10,jdbcType=VARCHAR}");
        }

        if (record.getExtend11() != null) {
            sql.VALUES("extend_11", "#{extend11,jdbcType=VARCHAR}");
        }

        if (record.getExtend12() != null) {
            sql.VALUES("extend_12", "#{extend12,jdbcType=VARCHAR}");
        }

        if (record.getExtend13() != null) {
            sql.VALUES("extend_13", "#{extend13,jdbcType=VARCHAR}");
        }

        if (record.getExtend14() != null) {
            sql.VALUES("extend_14", "#{extend14,jdbcType=VARCHAR}");
        }

        if (record.getExtend15() != null) {
            sql.VALUES("extend_15", "#{extend15,jdbcType=VARCHAR}");
        }

        if (record.getExtend16() != null) {
            sql.VALUES("extend_16", "#{extend16,jdbcType=VARCHAR}");
        }

        if (record.getExtend17() != null) {
            sql.VALUES("extend_17", "#{extend17,jdbcType=VARCHAR}");
        }

        if (record.getExtend18() != null) {
            sql.VALUES("extend_18", "#{extend18,jdbcType=VARCHAR}");
        }

        if (record.getExtend19() != null) {
            sql.VALUES("extend_19", "#{extend19,jdbcType=VARCHAR}");
        }

        if (record.getExtend20() != null) {
            sql.VALUES("extend_20", "#{extend20,jdbcType=VARCHAR}");
        }

        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserDetails record) {
        SQL sql = new SQL();
        sql.UPDATE("sys_user_details");

        if (record.getTenantId() != null) {
            sql.SET("tenant_id = #{tenantId,jdbcType=BIGINT}");
        }

        if (record.getMajorId() != null) {
            sql.SET("major_id = #{majorId,jdbcType=BIGINT}");
        }

        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=BIT}");
        }

        if (record.getBirthday() != null) {
            sql.SET("birthday = #{birthday,jdbcType=VARCHAR}");
        }

        if (record.getEntryDate() != null) {
            sql.SET("entry_date = #{entryDate,jdbcType=VARCHAR}");
        }

        if (record.getWorkCode() != null) {
            sql.SET("work_code = #{workCode,jdbcType=VARCHAR}");
        }

        if (record.getOperatingPost() != null) {
            sql.SET("operating_post = #{operatingPost,jdbcType=INTEGER}");
        }

        if (record.getExtend1() != null) {
            sql.SET("extend_1 = #{extend1,jdbcType=VARCHAR}");
        }

        if (record.getExtend2() != null) {
            sql.SET("extend_2 = #{extend2,jdbcType=VARCHAR}");
        }

        if (record.getExtend3() != null) {
            sql.SET("extend_3 = #{extend3,jdbcType=VARCHAR}");
        }

        if (record.getExtend4() != null) {
            sql.SET("extend_4 = #{extend4,jdbcType=VARCHAR}");
        }

        if (record.getExtend5() != null) {
            sql.SET("extend_5 = #{extend5,jdbcType=VARCHAR}");
        }

        if (record.getExtend6() != null) {
            sql.SET("extend_6 = #{extend6,jdbcType=VARCHAR}");
        }

        if (record.getExtend7() != null) {
            sql.SET("extend_7 = #{extend7,jdbcType=VARCHAR}");
        }

        if (record.getExtend8() != null) {
            sql.SET("extend_8 = #{extend8,jdbcType=VARCHAR}");
        }

        if (record.getExtend9() != null) {
            sql.SET("extend_9 = #{extend9,jdbcType=VARCHAR}");
        }

        if (record.getExtend10() != null) {
            sql.SET("extend_10 = #{extend10,jdbcType=VARCHAR}");
        }

        if (record.getExtend11() != null) {
            sql.SET("extend_11 = #{extend11,jdbcType=VARCHAR}");
        }

        if (record.getExtend12() != null) {
            sql.SET("extend_12 = #{extend12,jdbcType=VARCHAR}");
        }

        if (record.getExtend13() != null) {
            sql.SET("extend_13 = #{extend13,jdbcType=VARCHAR}");
        }

        if (record.getExtend14() != null) {
            sql.SET("extend_14 = #{extend14,jdbcType=VARCHAR}");
        }

        if (record.getExtend15() != null) {
            sql.SET("extend_15 = #{extend15,jdbcType=VARCHAR}");
        }

        if (record.getExtend16() != null) {
            sql.SET("extend_16 = #{extend16,jdbcType=VARCHAR}");
        }

        if (record.getExtend17() != null) {
            sql.SET("extend_17 = #{extend17,jdbcType=VARCHAR}");
        }

        if (record.getExtend18() != null) {
            sql.SET("extend_18 = #{extend18,jdbcType=VARCHAR}");
        }

        if (record.getExtend19() != null) {
            sql.SET("extend_19 = #{extend19,jdbcType=VARCHAR}");
        }

        if (record.getExtend20() != null) {
            sql.SET("extend_20 = #{extend20,jdbcType=VARCHAR}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }
}