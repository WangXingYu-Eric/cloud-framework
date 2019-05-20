package com.sinosoft.newstandard.common.web.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = -4127671108649731017L;

    private Long id;

    private Long tenantId;

    private Long parentId;

    private Long detailsId;

    private String username;

    private String password;

    private Date loginTime;

    private Date lastLoginTime;

    private Integer failedCount;

    private String nameCn;

    private String nameEn;

    private String email;

    private String phone;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private String remark;

    private Integer status;

    private Boolean deleteFlag;

    private Long sequence;

    private Date createTime;

    private Long createUser;

    private Date modifyTime;

    private Long modifyUser;

    private com.sinosoft.newstandard.common.web.entity.UserDetails userDetails;

    private Set<GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired == null ? true : accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked == null ? true : accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired == null ? true : credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled == null ? true : enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public com.sinosoft.newstandard.common.web.entity.UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(com.sinosoft.newstandard.common.web.entity.UserDetails userDetails) {
        this.userDetails = userDetails;
    }

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Long detailsId) {
        this.detailsId = detailsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /*public Boolean getEnabled() {
        return enabled;
    }*/

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /*public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }*/

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /*public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }*/

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /*public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }*/

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getDetailsId() == null ? other.getDetailsId() == null : this.getDetailsId().equals(other.getDetailsId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
                && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
                && (this.getFailedCount() == null ? other.getFailedCount() == null : this.getFailedCount().equals(other.getFailedCount()))
                && (this.getNameCn() == null ? other.getNameCn() == null : this.getNameCn().equals(other.getNameCn()))
                && (this.getNameEn() == null ? other.getNameEn() == null : this.getNameEn().equals(other.getNameEn()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (Boolean.compare(this.isEnabled(), other.isEnabled()) == 0)
                && (Boolean.compare(this.isAccountNonExpired(), other.isAccountNonExpired()) == 0)
                && (Boolean.compare(this.isAccountNonLocked(), other.isAccountNonLocked()) == 0)
                && (Boolean.compare(this.isCredentialsNonExpired(), other.isCredentialsNonExpired()) == 0)
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
                && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
                && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
                && (this.getModifyUser() == null ? other.getModifyUser() == null : this.getModifyUser().equals(other.getModifyUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getDetailsId() == null) ? 0 : getDetailsId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getFailedCount() == null) ? 0 : getFailedCount().hashCode());
        result = prime * result + ((getNameCn() == null) ? 0 : getNameCn().hashCode());
        result = prime * result + ((getNameEn() == null) ? 0 : getNameEn().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + (Boolean.hashCode(isEnabled()));
        result = prime * result + (Boolean.hashCode(isAccountNonExpired()));
        result = prime * result + (Boolean.hashCode(isAccountNonLocked()));
        result = prime * result + (Boolean.hashCode(isCredentialsNonExpired()));
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifyUser() == null) ? 0 : getModifyUser().hashCode());
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
        sb.append(", parentId=").append(parentId);
        sb.append(", detailsId=").append(detailsId);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", failedCount=").append(failedCount);
        sb.append(", nameCn=").append(nameCn);
        sb.append(", nameEn=").append(nameEn);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", enabled=").append(enabled);
        sb.append(", accountNonExpired=").append(accountNonExpired);
        sb.append(", accountNonLocked=").append(accountNonLocked);
        sb.append(", credentialsNonExpired=").append(credentialsNonExpired);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", sequence=").append(sequence);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", modifyUser=").append(modifyUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}