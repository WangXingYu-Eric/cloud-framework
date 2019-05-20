package com.sinosoft.newstandard.common.web.controller;

import com.sinosoft.newstandard.common.enumeration.Constant;
import com.sinosoft.newstandard.common.util.JacksonUtils;
import com.sinosoft.newstandard.common.util.UrlUtils;
import com.sinosoft.newstandard.common.web.entity.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@WebServlet("/preAuthenticate")
public class PreAuthenticateServlet extends HttpServlet {

    private static final long serialVersionUID = -8324452748565394312L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        //把新授权放入header
        String newAuthorization = request.getHeader(Constant.NEW_AUTHORIZATION);
        if (StringUtils.isNotEmpty(newAuthorization)) {
            response.setHeader(Constant.NEW_AUTHORIZATION, UrlUtils.getURLEncoderString(newAuthorization));
        }
        PrintWriter writer = response.getWriter();
        writer.print(JacksonUtils.toJson(Result.success()));
    }

}
