package com.sinosoft.newstandard.common.web.controller;

import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.exception.BaseException;
import com.sinosoft.newstandard.common.util.JacksonUtils;
import com.sinosoft.newstandard.common.util.UrlUtils;
import com.sinosoft.newstandard.common.web.entity.Result;
import com.sinosoft.newstandard.common.web.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected HttpServletRequest request;

    protected HttpSession session;

    protected HttpServletResponse response;

    private User currentUser;

    private List<String> roleCodes;

    @ModelAttribute
    public void addAttributes(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        this.request = request;
        this.session = session;
        this.response = response;
        Optional.ofNullable(this.request.getHeader(Constant.NEW_AUTHORIZATION)).ifPresent(newAuthorization -> {
            //把新授权放入header
            this.response.setHeader(Constant.NEW_AUTHORIZATION, UrlUtils.getURLEncoderString(newAuthorization));
        });
        Optional.ofNullable(this.request.getHeader(Constant.CURRENT_USER)).ifPresent(currentUserJson -> {
            //当前登录用户
            this.currentUser = JacksonUtils.fromJson(currentUserJson, User.class);
        });
        Optional.ofNullable(this.request.getHeader(Constant.ROLE_CODES)).ifPresent(roleCodeJson -> {
            //当前登录用户所拥有的角色编码集合
            this.roleCodes = JacksonUtils.fromJson(roleCodeJson, List.class);
        });
    }

    /**
     * 全局异常捕捉处理.
     */
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public Result errorHandler(RuntimeException ex) {
        if (ex instanceof BaseException) {
            return Result.fail(ex);
        } else {
            return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpSession getSession() {
        return session;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public User getCurrentUser() throws BaseException {
        if (currentUser == null) {
            logger.warn("当前登录用户为空");
        }
        return currentUser;
    }

    public List<String> getRoleCodes() throws BaseException {
        if (roleCodes == null) {
            logger.warn("未匹配任何角色");
        }
        return roleCodes;
    }

}
