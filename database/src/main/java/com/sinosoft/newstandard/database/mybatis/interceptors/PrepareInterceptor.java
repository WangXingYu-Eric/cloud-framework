package com.sinosoft.newstandard.database.mybatis.interceptors;

import com.sinosoft.newstandard.common.util.snowflake.SnowflakeIdWorker;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * @author Eric
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class PrepareInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //注解中method的值
        String methodName = invocation.getMethod().getName();
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if ("update".equals(methodName)) {
            Object object = invocation.getArgs()[1];
//            Date currentDate = new Date(System.currentTimeMillis());
            //对有要求的字段填值
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                long id = snowflakeIdWorker.nextId();
                operateId(object, id);
                operateOrderNumber(object, id);
//                operateTenantId(object);
//                operateCreateUser(object);
//                operateCreateTime(object, currentDate);
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
//                operateModifyUser(object);
//                operateModifyTime(object, currentDate);
            }
        }
        return invocation.proceed();
    }

    private void operateId(Object target, Long id) throws Throwable {
        try {
            Field fieldId = target.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(target, id);
            logger.debug("插入操作前设置id:{}", id);
        } catch (NoSuchFieldException e) {
            logger.debug("没有[id]字段,插入操作前无需设置id");
        }
    }

    private void operateOrderNumber(Object target, Long orderNumber) throws Throwable {
        try {
            Field fieldOrderNumber = target.getClass().getDeclaredField("orderNumber");
            fieldOrderNumber.setAccessible(true);
            fieldOrderNumber.set(target, orderNumber);
            logger.debug("插入操作前设置orderNumber:{}", orderNumber);
        } catch (NoSuchFieldException e) {
            logger.debug("没有[order_number]字段,插入操作前无需设置order_number");
        }
    }

//    private void operateTenantId(Object target) throws Throwable {
//        User currentUser = SecurityUtil.getCurrentUser();
//        if (currentUser != null) {
//            try {
//                Field fieldTenantId = target.getClass().getDeclaredField("tenantId");
//                fieldTenantId.setAccessible(true);
//                if (fieldTenantId.get(target) == null) {
//                    Tenant tenant = (Tenant) RequestContextHolder.getRequestAttributes().getAttribute("tenant", RequestAttributes.SCOPE_SESSION);
//                    fieldTenantId.set(target, tenant.getId());
//                    logger.debug("插入操作设置tenant_id:{}", tenant.getId());
//                }
//            } catch (NoSuchFieldException e) {
//                logger.debug("没有[tenant_id]字段,插入操作时无需设置tenant_id");
//            }
//        } else {
//            logger.debug("用户未登录.");
//        }
//    }
//
//    private void operateCreateUser(Object target) throws Throwable {
//        User currentUser = SecurityUtil.getCurrentUser();
//        if (currentUser != null) {
//            try {
//                Field fieldCreateUser = target.getClass().getDeclaredField("createUser");
//                fieldCreateUser.setAccessible(true);
//                if (fieldCreateUser.get(target) == null) {
//                    fieldCreateUser.set(target, currentUser.getId());
//                    logger.debug("插入操作设置create_user:{}", currentUser.getId());
//                }
//            } catch (NoSuchFieldException e) {
//                logger.debug("没有[create_user]字段,插入操作时无需设置create_user");
//            }
//        } else {
//            logger.debug("用户未登录.");
//        }
//    }
//
//    private void operateCreateTime(Object target, Date currentDate) throws Throwable {
//        try {
//            Field fieldCreateTime = target.getClass().getDeclaredField("createTime");
//            fieldCreateTime.setAccessible(true);
//            if (fieldCreateTime.get(target) == null) {
//                fieldCreateTime.set(target, currentDate);
//                logger.debug("插入操作时设置create_time:{}", currentDate);
//            }
//        } catch (NoSuchFieldException e) {
//            logger.debug("没有[create_time]字段,插入操作时无需设置create_time");
//        }
//    }
//
//    private void operateModifyUser(Object target) throws Throwable {
//        User currentUser = SecurityUtil.getCurrentUser();
//        if (currentUser != null) {
//            try {
//                Field fieldCreateUser = target.getClass().getDeclaredField("modifyUser");
//                fieldCreateUser.setAccessible(true);
//                if (fieldCreateUser.get(target) == null) {
//                    fieldCreateUser.set(target, currentUser.getId());
//                    logger.debug("更新操作设置modify_user:{}", currentUser.getId());
//                }
//            } catch (NoSuchFieldException e) {
//                logger.debug("没有[modify_user]字段,更新操作时无需设置modify_user");
//            }
//        } else {
//            logger.debug("用户未登录.");
//        }
//    }
//
//    private void operateModifyTime(Object target, Date currentDate) throws Throwable {
//        try {
//            Field fieldModifyTime = target.getClass().getDeclaredField("modifyTime");
//            fieldModifyTime.setAccessible(true);
//            if (fieldModifyTime.get(target) == null) {
//                fieldModifyTime.set(target, currentDate);
//                logger.debug("更新操作时设置modify_time:{}", currentDate);
//            }
//        } catch (NoSuchFieldException e) {
//            logger.debug("没有[modify_time]字段,更新操作时无需设置modify_time");
//        }
//    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}