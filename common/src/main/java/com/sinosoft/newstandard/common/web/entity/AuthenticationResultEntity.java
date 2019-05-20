package com.sinosoft.newstandard.common.web.entity;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class AuthenticationResultEntity extends BaseEntity {

    private static final long serialVersionUID = -2614951642196166178L;

    /**
     * 是否权限许可
     */
    private boolean permissive;

    /**
     * 新的授权
     */
    private String newAuthorization;

    public boolean isPermissive() {
        return permissive;
    }

    public void setPermissive(boolean permissive) {
        this.permissive = permissive;
    }

    public String getNewAuthorization() {
        return newAuthorization;
    }

    public void setNewAuthorization(String newAuthorization) {
        this.newAuthorization = newAuthorization;
    }
}
