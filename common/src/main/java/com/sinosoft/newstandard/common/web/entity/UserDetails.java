package com.sinosoft.newstandard.common.web.entity;

import java.io.Serializable;

public class UserDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -71799347531219663L;

    private Long id;

    private Long tenantId;

    private Long majorId;

    private Boolean gender;

    private String birthday;

    private String entryDate;

    private String workCode;

    private Integer operatingPost;

    private String extend1;

    private String extend2;

    private String extend3;

    private String extend4;

    private String extend5;

    private String extend6;

    private String extend7;

    private String extend8;

    private String extend9;

    private String extend10;

    private String extend11;

    private String extend12;

    private String extend13;

    private String extend14;

    private String extend15;

    private String extend16;

    private String extend17;

    private String extend18;

    private String extend19;

    private String extend20;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate == null ? null : entryDate.trim();
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode == null ? null : workCode.trim();
    }

    public Integer getOperatingPost() {
        return operatingPost;
    }

    public void setOperatingPost(Integer operatingPost) {
        this.operatingPost = operatingPost;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3 == null ? null : extend3.trim();
    }

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4 == null ? null : extend4.trim();
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5 == null ? null : extend5.trim();
    }

    public String getExtend6() {
        return extend6;
    }

    public void setExtend6(String extend6) {
        this.extend6 = extend6 == null ? null : extend6.trim();
    }

    public String getExtend7() {
        return extend7;
    }

    public void setExtend7(String extend7) {
        this.extend7 = extend7 == null ? null : extend7.trim();
    }

    public String getExtend8() {
        return extend8;
    }

    public void setExtend8(String extend8) {
        this.extend8 = extend8 == null ? null : extend8.trim();
    }

    public String getExtend9() {
        return extend9;
    }

    public void setExtend9(String extend9) {
        this.extend9 = extend9 == null ? null : extend9.trim();
    }

    public String getExtend10() {
        return extend10;
    }

    public void setExtend10(String extend10) {
        this.extend10 = extend10 == null ? null : extend10.trim();
    }

    public String getExtend11() {
        return extend11;
    }

    public void setExtend11(String extend11) {
        this.extend11 = extend11 == null ? null : extend11.trim();
    }

    public String getExtend12() {
        return extend12;
    }

    public void setExtend12(String extend12) {
        this.extend12 = extend12 == null ? null : extend12.trim();
    }

    public String getExtend13() {
        return extend13;
    }

    public void setExtend13(String extend13) {
        this.extend13 = extend13 == null ? null : extend13.trim();
    }

    public String getExtend14() {
        return extend14;
    }

    public void setExtend14(String extend14) {
        this.extend14 = extend14 == null ? null : extend14.trim();
    }

    public String getExtend15() {
        return extend15;
    }

    public void setExtend15(String extend15) {
        this.extend15 = extend15 == null ? null : extend15.trim();
    }

    public String getExtend16() {
        return extend16;
    }

    public void setExtend16(String extend16) {
        this.extend16 = extend16 == null ? null : extend16.trim();
    }

    public String getExtend17() {
        return extend17;
    }

    public void setExtend17(String extend17) {
        this.extend17 = extend17 == null ? null : extend17.trim();
    }

    public String getExtend18() {
        return extend18;
    }

    public void setExtend18(String extend18) {
        this.extend18 = extend18 == null ? null : extend18.trim();
    }

    public String getExtend19() {
        return extend19;
    }

    public void setExtend19(String extend19) {
        this.extend19 = extend19 == null ? null : extend19.trim();
    }

    public String getExtend20() {
        return extend20;
    }

    public void setExtend20(String extend20) {
        this.extend20 = extend20 == null ? null : extend20.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserDetails other = (UserDetails) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
                && (this.getMajorId() == null ? other.getMajorId() == null : this.getMajorId().equals(other.getMajorId()))
                && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
                && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
                && (this.getEntryDate() == null ? other.getEntryDate() == null : this.getEntryDate().equals(other.getEntryDate()))
                && (this.getWorkCode() == null ? other.getWorkCode() == null : this.getWorkCode().equals(other.getWorkCode()))
                && (this.getOperatingPost() == null ? other.getOperatingPost() == null : this.getOperatingPost().equals(other.getOperatingPost()))
                && (this.getExtend1() == null ? other.getExtend1() == null : this.getExtend1().equals(other.getExtend1()))
                && (this.getExtend2() == null ? other.getExtend2() == null : this.getExtend2().equals(other.getExtend2()))
                && (this.getExtend3() == null ? other.getExtend3() == null : this.getExtend3().equals(other.getExtend3()))
                && (this.getExtend4() == null ? other.getExtend4() == null : this.getExtend4().equals(other.getExtend4()))
                && (this.getExtend5() == null ? other.getExtend5() == null : this.getExtend5().equals(other.getExtend5()))
                && (this.getExtend6() == null ? other.getExtend6() == null : this.getExtend6().equals(other.getExtend6()))
                && (this.getExtend7() == null ? other.getExtend7() == null : this.getExtend7().equals(other.getExtend7()))
                && (this.getExtend8() == null ? other.getExtend8() == null : this.getExtend8().equals(other.getExtend8()))
                && (this.getExtend9() == null ? other.getExtend9() == null : this.getExtend9().equals(other.getExtend9()))
                && (this.getExtend10() == null ? other.getExtend10() == null : this.getExtend10().equals(other.getExtend10()))
                && (this.getExtend11() == null ? other.getExtend11() == null : this.getExtend11().equals(other.getExtend11()))
                && (this.getExtend12() == null ? other.getExtend12() == null : this.getExtend12().equals(other.getExtend12()))
                && (this.getExtend13() == null ? other.getExtend13() == null : this.getExtend13().equals(other.getExtend13()))
                && (this.getExtend14() == null ? other.getExtend14() == null : this.getExtend14().equals(other.getExtend14()))
                && (this.getExtend15() == null ? other.getExtend15() == null : this.getExtend15().equals(other.getExtend15()))
                && (this.getExtend16() == null ? other.getExtend16() == null : this.getExtend16().equals(other.getExtend16()))
                && (this.getExtend17() == null ? other.getExtend17() == null : this.getExtend17().equals(other.getExtend17()))
                && (this.getExtend18() == null ? other.getExtend18() == null : this.getExtend18().equals(other.getExtend18()))
                && (this.getExtend19() == null ? other.getExtend19() == null : this.getExtend19().equals(other.getExtend19()))
                && (this.getExtend20() == null ? other.getExtend20() == null : this.getExtend20().equals(other.getExtend20()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getMajorId() == null) ? 0 : getMajorId().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getEntryDate() == null) ? 0 : getEntryDate().hashCode());
        result = prime * result + ((getWorkCode() == null) ? 0 : getWorkCode().hashCode());
        result = prime * result + ((getOperatingPost() == null) ? 0 : getOperatingPost().hashCode());
        result = prime * result + ((getExtend1() == null) ? 0 : getExtend1().hashCode());
        result = prime * result + ((getExtend2() == null) ? 0 : getExtend2().hashCode());
        result = prime * result + ((getExtend3() == null) ? 0 : getExtend3().hashCode());
        result = prime * result + ((getExtend4() == null) ? 0 : getExtend4().hashCode());
        result = prime * result + ((getExtend5() == null) ? 0 : getExtend5().hashCode());
        result = prime * result + ((getExtend6() == null) ? 0 : getExtend6().hashCode());
        result = prime * result + ((getExtend7() == null) ? 0 : getExtend7().hashCode());
        result = prime * result + ((getExtend8() == null) ? 0 : getExtend8().hashCode());
        result = prime * result + ((getExtend9() == null) ? 0 : getExtend9().hashCode());
        result = prime * result + ((getExtend10() == null) ? 0 : getExtend10().hashCode());
        result = prime * result + ((getExtend11() == null) ? 0 : getExtend11().hashCode());
        result = prime * result + ((getExtend12() == null) ? 0 : getExtend12().hashCode());
        result = prime * result + ((getExtend13() == null) ? 0 : getExtend13().hashCode());
        result = prime * result + ((getExtend14() == null) ? 0 : getExtend14().hashCode());
        result = prime * result + ((getExtend15() == null) ? 0 : getExtend15().hashCode());
        result = prime * result + ((getExtend16() == null) ? 0 : getExtend16().hashCode());
        result = prime * result + ((getExtend17() == null) ? 0 : getExtend17().hashCode());
        result = prime * result + ((getExtend18() == null) ? 0 : getExtend18().hashCode());
        result = prime * result + ((getExtend19() == null) ? 0 : getExtend19().hashCode());
        result = prime * result + ((getExtend20() == null) ? 0 : getExtend20().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", majorId=").append(majorId);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", entryDate=").append(entryDate);
        sb.append(", workCode=").append(workCode);
        sb.append(", operatingPost=").append(operatingPost);
        sb.append(", extend1=").append(extend1);
        sb.append(", extend2=").append(extend2);
        sb.append(", extend3=").append(extend3);
        sb.append(", extend4=").append(extend4);
        sb.append(", extend5=").append(extend5);
        sb.append(", extend6=").append(extend6);
        sb.append(", extend7=").append(extend7);
        sb.append(", extend8=").append(extend8);
        sb.append(", extend9=").append(extend9);
        sb.append(", extend10=").append(extend10);
        sb.append(", extend11=").append(extend11);
        sb.append(", extend12=").append(extend12);
        sb.append(", extend13=").append(extend13);
        sb.append(", extend14=").append(extend14);
        sb.append(", extend15=").append(extend15);
        sb.append(", extend16=").append(extend16);
        sb.append(", extend17=").append(extend17);
        sb.append(", extend18=").append(extend18);
        sb.append(", extend19=").append(extend19);
        sb.append(", extend20=").append(extend20);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}