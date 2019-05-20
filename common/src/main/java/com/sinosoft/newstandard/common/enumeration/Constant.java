package com.sinosoft.newstandard.common.enumeration;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class Constant {

    public final static String BASE_AUTH = "Basic dGVzdDoxMjM0NTY=";

    /**
     * Authorization是以'Bearer '开头的.
     */
    public static final String BEARER_VLAUE = "Bearer ";

    /**
     * 去除Authorization中的'Bearer '所需要的起始位置.
     */
    public static final int BEARER_BEGIN_INDEX = 7;

    /**
     * 自定义请求头:存放刷新后新的access_token
     */
    public final static String NEW_AUTHORIZATION = "newAuthorization";

    /**
     * 自定义请求头:存放当前user对象
     */
    public final static String CURRENT_USER = "currentUser";

    /**
     * 自定义请求头:存放当前user对象所拥有的角色编码集合
     */
    public final static String ROLE_CODES = "roleCodes";

}
