package com.sinosoft.newstandard.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class UrlUtils {

    /**
     * URL 解码
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (StringUtils.isEmpty(str)) {
            return result;
        }
        try {
            result = java.net.URLDecoder.decode(str, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (StringUtils.isEmpty(str)) {
            return result;
        }
        try {
            result = java.net.URLEncoder.encode(str, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String str = "这是testStr";
        System.out.println(str);
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(getURLEncoderString(str)));
    }

}
