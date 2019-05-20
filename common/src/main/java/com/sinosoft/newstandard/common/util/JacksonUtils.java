package com.sinosoft.newstandard.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 */
public class JacksonUtils extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    /**
     * 当前类的实例持有者(静态内部类,延迟加载,懒汉式,线程安全的单例模式).
     */
    private static final class JsonMapperHolder {
        private static final JacksonUtils INSTANCE = new JacksonUtils();
    }

    private JacksonUtils() {
        // 为Null时不序列化
        this.setSerializationInclusion(Include.ALWAYS);
        // 允许单引号
        this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许不带引号的字段名称
        this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 设置时区
        this.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        //this.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 遇到空值处理为空串
        /*this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });*/
        //格式化Date类型的数据
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule simpleModule = new SimpleModule();
        //序列换成Json时,将所有的Long变成String因为JS中的数字类型不能包含所有的Long值.
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.registerModule(simpleModule);
    }

    /**
     * Object可以是POJO,也可以是Collection或数组.
     * 如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     */
    private String toJsonString(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("写入json字符串错误:" + object, e);
            return null;
        }
    }

    /**
     * 输出JSONP格式数据.
     */
    private String toJsonpString(String functionName, Object object) {
        return toJsonString(new JSONPObject(functionName, object));
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * 如需反序列化复杂Collection如List<MyBean>,请使用fromJson(String,JavaType).
     */
    private <T> T fromJsonString(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString) || "<CLOB>".equals(jsonString)) {
            return null;
        }
        try {
            return this.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("解析json字符串错误:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>,先使用函数createCollectionType构造类型,然后调用本函数.
     *
     * @see #createCollectionType(Class, Class...)
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJsonString(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString) || "<CLOB>".equals(jsonString)) {
            return null;
        }
        try {
            return (T) this.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("解析json字符串错误:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造泛型的Collection Type如:
     * ArrayList<MyBean>,则调用constructCollectionType(ArrayList.class,MyBean.class).
     * HashMap<String,MyBean>,则调用(HashMap.class,String.class, MyBean.class).
     */
    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 当JSON里只含有Bean的部分属性時,更新一个已存在Bean,只覆盖该部分的属性.
     */
    @SuppressWarnings("unchecked")
    public <T> T update(String jsonString, T object) {
        try {
            return (T) this.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            logger.warn("更新json字符串:" + jsonString + "时,对象" + object + "错误.", e);
        }
        return null;
    }

    /**
     * 设定是否使用Enum的toString函数来读写Enum,
     * 为False实时使用Enum的name()函数来读写Enum,默认为False.
     * 注意本函数一定要在Mapper创建后,所有的读写动作之前调用.
     */
    public JacksonUtils enableEnumUseToString() {
        this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return this;
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return this;
    }

    /**
     * 获取当前实例.
     */
    public static JacksonUtils getInstance() {
        return JsonMapperHolder.INSTANCE;
    }

    /**
     * 对象转换为JSON字符串.
     */
    public static String toJson(Object object) {
        return JacksonUtils.getInstance().toJsonString(object);
    }

    /**
     * 对象转换为JSONP字符串.
     */
    public static String toJsonp(String functionName, Object object) {
        return JacksonUtils.getInstance().toJsonpString(functionName, object);
    }

    /**
     * JSON字符串转换为对象.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String jsonString, Class<?> clazz) {
        return (T) JacksonUtils.getInstance().fromJsonString(jsonString, clazz);
    }

    /**
     * JSON字符串转换为 List<Map<String, Object>>.
     */
    public static List<Map<String, Object>> fromJsonForMapList(String jsonString) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.startsWith(jsonString, "{")) {
            Map<String, Object> map = fromJson(jsonString, Map.class);
            if (map != null) {
                result.add(map);
            }
        } else if (StringUtils.startsWith(jsonString, "[")) {
            List<Map<String, Object>> list = fromJson(jsonString, List.class);
            if (list != null) {
                result = list;
            }
        }
        return result;
    }

}
