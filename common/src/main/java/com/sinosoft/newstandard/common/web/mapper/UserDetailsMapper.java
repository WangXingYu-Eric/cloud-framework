package com.sinosoft.newstandard.common.web.mapper;

import com.sinosoft.newstandard.common.web.entity.UserDetails;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserDetailsMapper extends BaseMapper<UserDetails> {
    @Delete({
            "delete from sys_user_details",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into sys_user_details (id, tenant_id, ",
            "major_id, gender, birthday, ",
            "entry_date, work_code, ",
            "operating_post, extend_1, ",
            "extend_2, extend_3, ",
            "extend_4, extend_5, ",
            "extend_6, extend_7, ",
            "extend_8, extend_9, ",
            "extend_10, extend_11, ",
            "extend_12, extend_13, ",
            "extend_14, extend_15, ",
            "extend_16, extend_17, ",
            "extend_18, extend_19, ",
            "extend_20)",
            "values (#{id,jdbcType=BIGINT}, #{tenantId,jdbcType=BIGINT}, ",
            "#{majorId,jdbcType=BIGINT}, #{gender,jdbcType=BIT}, #{birthday,jdbcType=VARCHAR}, ",
            "#{entryDate,jdbcType=VARCHAR}, #{workCode,jdbcType=VARCHAR}, ",
            "#{operatingPost,jdbcType=INTEGER}, #{extend1,jdbcType=VARCHAR}, ",
            "#{extend2,jdbcType=VARCHAR}, #{extend3,jdbcType=VARCHAR}, ",
            "#{extend4,jdbcType=VARCHAR}, #{extend5,jdbcType=VARCHAR}, ",
            "#{extend6,jdbcType=VARCHAR}, #{extend7,jdbcType=VARCHAR}, ",
            "#{extend8,jdbcType=VARCHAR}, #{extend9,jdbcType=VARCHAR}, ",
            "#{extend10,jdbcType=VARCHAR}, #{extend11,jdbcType=VARCHAR}, ",
            "#{extend12,jdbcType=VARCHAR}, #{extend13,jdbcType=VARCHAR}, ",
            "#{extend14,jdbcType=VARCHAR}, #{extend15,jdbcType=VARCHAR}, ",
            "#{extend16,jdbcType=VARCHAR}, #{extend17,jdbcType=VARCHAR}, ",
            "#{extend18,jdbcType=VARCHAR}, #{extend19,jdbcType=VARCHAR}, ",
            "#{extend20,jdbcType=VARCHAR})"
    })
    int insert(UserDetails record);

    @InsertProvider(type = UserDetailsSqlProvider.class, method = "insertSelective")
    int insertSelective(UserDetails record);

    @Select({
            "select",
            "id, tenant_id, major_id, gender, birthday, entry_date, work_code, operating_post, ",
            "extend_1, extend_2, extend_3, extend_4, extend_5, extend_6, extend_7, extend_8, ",
            "extend_9, extend_10, extend_11, extend_12, extend_13, extend_14, extend_15, ",
            "extend_16, extend_17, extend_18, extend_19, extend_20",
            "from sys_user_details",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "major_id", property = "majorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.BIT),
            @Result(column = "birthday", property = "birthday", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entry_date", property = "entryDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "work_code", property = "workCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operating_post", property = "operatingPost", jdbcType = JdbcType.INTEGER),
            @Result(column = "extend_1", property = "extend1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_2", property = "extend2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_3", property = "extend3", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_4", property = "extend4", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_5", property = "extend5", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_6", property = "extend6", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_7", property = "extend7", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_8", property = "extend8", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_9", property = "extend9", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_10", property = "extend10", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_11", property = "extend11", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_12", property = "extend12", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_13", property = "extend13", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_14", property = "extend14", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_15", property = "extend15", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_16", property = "extend16", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_17", property = "extend17", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_18", property = "extend18", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_19", property = "extend19", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_20", property = "extend20", jdbcType = JdbcType.VARCHAR)
    })
    UserDetails selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserDetailsSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserDetails record);

    @Update({
            "update sys_user_details",
            "set tenant_id = #{tenantId,jdbcType=BIGINT},",
            "major_id = #{majorId,jdbcType=BIGINT},",
            "gender = #{gender,jdbcType=BIT},",
            "birthday = #{birthday,jdbcType=VARCHAR},",
            "entry_date = #{entryDate,jdbcType=VARCHAR},",
            "work_code = #{workCode,jdbcType=VARCHAR},",
            "operating_post = #{operatingPost,jdbcType=INTEGER},",
            "extend_1 = #{extend1,jdbcType=VARCHAR},",
            "extend_2 = #{extend2,jdbcType=VARCHAR},",
            "extend_3 = #{extend3,jdbcType=VARCHAR},",
            "extend_4 = #{extend4,jdbcType=VARCHAR},",
            "extend_5 = #{extend5,jdbcType=VARCHAR},",
            "extend_6 = #{extend6,jdbcType=VARCHAR},",
            "extend_7 = #{extend7,jdbcType=VARCHAR},",
            "extend_8 = #{extend8,jdbcType=VARCHAR},",
            "extend_9 = #{extend9,jdbcType=VARCHAR},",
            "extend_10 = #{extend10,jdbcType=VARCHAR},",
            "extend_11 = #{extend11,jdbcType=VARCHAR},",
            "extend_12 = #{extend12,jdbcType=VARCHAR},",
            "extend_13 = #{extend13,jdbcType=VARCHAR},",
            "extend_14 = #{extend14,jdbcType=VARCHAR},",
            "extend_15 = #{extend15,jdbcType=VARCHAR},",
            "extend_16 = #{extend16,jdbcType=VARCHAR},",
            "extend_17 = #{extend17,jdbcType=VARCHAR},",
            "extend_18 = #{extend18,jdbcType=VARCHAR},",
            "extend_19 = #{extend19,jdbcType=VARCHAR},",
            "extend_20 = #{extend20,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserDetails record);

    @Select({
            "select",
            "id, tenant_id, major_id, gender, birthday, entry_date, work_code, operating_post, ",
            "extend_1, extend_2, extend_3, extend_4, extend_5, extend_6, extend_7, extend_8, ",
            "extend_9, extend_10, extend_11, extend_12, extend_13, extend_14, extend_15, ",
            "extend_16, extend_17, extend_18, extend_19, extend_20",
            "from sys_user_details"
    })
    @Results(id = "userdetails", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "tenant_id", property = "tenantId", jdbcType = JdbcType.BIGINT),
            @Result(column = "major_id", property = "majorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.BIT),
            @Result(column = "birthday", property = "birthday", jdbcType = JdbcType.VARCHAR),
            @Result(column = "entry_date", property = "entryDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "work_code", property = "workCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operating_post", property = "operatingPost", jdbcType = JdbcType.INTEGER),
            @Result(column = "extend_1", property = "extend1", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_2", property = "extend2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_3", property = "extend3", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_4", property = "extend4", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_5", property = "extend5", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_6", property = "extend6", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_7", property = "extend7", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_8", property = "extend8", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_9", property = "extend9", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_10", property = "extend10", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_11", property = "extend11", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_12", property = "extend12", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_13", property = "extend13", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_14", property = "extend14", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_15", property = "extend15", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_16", property = "extend16", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_17", property = "extend17", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_18", property = "extend18", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_19", property = "extend19", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extend_20", property = "extend20", jdbcType = JdbcType.VARCHAR)
    })
    List<UserDetails> selectAll();

    //====================The Following Methods Are Added Manually====================
}