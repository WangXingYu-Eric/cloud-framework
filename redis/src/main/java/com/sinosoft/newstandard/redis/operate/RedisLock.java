package com.sinosoft.newstandard.redis.operate;

import com.sinosoft.newstandard.redis.CustomizedRedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@SuppressWarnings("unchecked")
public class RedisLock {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RedisOperations redisOperations;

    /**
     * 请求锁的超时时间(ms 毫秒)
     */
    private static final long TIME_OUT = 100;

    /**
     * 锁的有效时间(s)
     */
    private static final int EXPIRE = 60;

    /**
     * 释放锁的LUA脚本
     */
    private static final String UNLOCK_LUA;

    static {
        UNLOCK_LUA = "" +
                "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }

    /**
     * 锁标识对应的key
     */
    private String lockKey;

    private byte[] lockKeyBytes;

    /**
     * 锁对应的值
     */
    private String lockValue;

    private byte[] lockValueBytes;

    public RedisLock(RedisOperations redisOperations, String lockKey) {
        this.redisOperations = redisOperations;
        this.lockKey = CustomizedRedisCache.concatKey("redis_lock_prefix", lockKey);
        this.lockKeyBytes = lockKey.getBytes(StandardCharsets.UTF_8);
        this.lockValue = UUID.randomUUID().toString();
        this.lockValueBytes = lockValue.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 获取分布式锁(默认过期时间和超时时间),原子操作
     */
    public boolean tryLock() {
        return tryLock(EXPIRE, TIME_OUT);
    }

    /**
     * 获取分布式锁,原子操作
     *
     * @param expireTime 锁的过期时间(单位:秒)
     * @param timeOut    请求锁的超时时间(单位:毫秒)
     */
    public boolean tryLock(int expireTime, long timeOut) {
        // 系统当前时间,纳秒
        long timeSignal = System.nanoTime();
        int i = 0;
        try {
            while ((System.nanoTime() - timeSignal) < timeOut * 1000000) {
                if (i > 0) {
                    // 每次请求等待一段时间
                    try {
                        Random random = new Random();
                        Thread.sleep(10, random.nextInt(500000));
                    } catch (InterruptedException e) {
                        logger.warn("获取分布式锁操作的休眠被中断:", e);
                    }
                }
                RedisCallback<Boolean> callback = (connection) -> connection.set(lockKeyBytes, lockValueBytes, Expiration.seconds(expireTime), RedisStringCommands.SetOption.SET_IF_ABSENT);
                Boolean isLock = (Boolean) redisOperations.execute(callback);
                i++;
                if (isLock) {
                    logger.info("redis key=[{}] lock [true].", this.lockKey);
                    return isLock;
                }
            }
        } catch (Exception e) {
            logger.error("redis key=[{}] lock error.message:{}", this.lockKey, e.getMessage());
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void releaseLock() {
        try {
            RedisCallback<Boolean> callback = (connection) -> connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, lockKeyBytes, lockValueBytes);
            Boolean isRelease = (Boolean) redisOperations.execute(callback);
            if (isRelease) {
                logger.info("redis key=[{}] release [true].", this.lockKey);
            } else {
                logger.info("redis key=[{}] already release or don't get lock.", this.lockKey);
            }
        } catch (Exception e) {
            logger.error("redis key=[{}] release error.message:{}", this.lockKey, e.getMessage());
        }
    }

}
