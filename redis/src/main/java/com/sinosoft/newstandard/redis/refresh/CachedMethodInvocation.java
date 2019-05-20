package com.sinosoft.newstandard.redis.refresh;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <code>@Cacheable</code>标注的方法执行信息<br/>
 * 用于主动刷新缓存时调用原始方法加载数据
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class CachedMethodInvocation implements Serializable {

    private static final long serialVersionUID = 4053574712960451939L;

    private Object key;
    private String targetBean;
    private String targetMethod;
    private List<Object> arguments;
    private List<String> parameterTypes = new ArrayList<>();

    public CachedMethodInvocation() {
    }

    public CachedMethodInvocation(Object key, Object targetBean, Method targetMethod, Class[] parameterTypes, Object[] arguments) {
        this.key = key;
        this.targetBean = targetBean.getClass().getName();
        this.targetMethod = targetMethod.getName();
        if (arguments != null && arguments.length != 0) {
            this.arguments = Arrays.asList(arguments);
        }
        if (parameterTypes != null && parameterTypes.length != 0) {
            for (Class clazz : parameterTypes) {
                this.parameterTypes.add(clazz.getName());
            }
        }
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getTargetBean() {
        return targetBean;
    }

    public void setTargetBean(String targetBean) {
        this.targetBean = targetBean;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    /**
     * 必须重写equals和hashCode方法,否则放到set集合里没法去重
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CachedMethodInvocation that = (CachedMethodInvocation) obj;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
