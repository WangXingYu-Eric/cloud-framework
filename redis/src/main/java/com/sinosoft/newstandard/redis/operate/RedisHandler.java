package com.sinosoft.newstandard.redis.operate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@SuppressWarnings({"unchecked", "ConstantConditions"})
public class RedisHandler implements RedisCommand<String, Object> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RedisTemplate redisTemplate;

    /**
     * 出异常,重复操作的次数
     */
    private final static Integer TIMES = 5;

    @Autowired
    public RedisHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断缓存中key是否存在
     */
    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 处理事务时锁定key
     */
    @Override
    public void watch(String key) {
        redisTemplate.watch(key);
    }

    /**
     * 基于时间戳的权重值
     */
    @Override
    public Double getCreateTimeScore(Long date) {
        return date / 100000.0;
    }

    /**
     * 获取缓存的类型
     */
    @Override
    public DataType getType(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 获取Redis中所有的键的key
     */
    @Override
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    /**
     * 获取Redis符合条件的key
     */
    @Override
    public Set<String> keys(String keyPattern) {
        return redisTemplate.keys(keyPattern);
    }

    /**
     * 设置过期时间 - 单位s
     */
    @Override
    public Boolean setExpireTime(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 修改key,如果不存在key或者没有修改成功则返回false
     */
    @Override
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    //==========String Start==========

    /**
     * 添加缓存
     */
    @Override
    public void set(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public Boolean setNX(String key, Object value) {
        return redisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    /**
     * 添加缓存,并设置过期时间 - 单位s
     */
    @Override
    public void set(String key, Object value, Long expireTime) {
        redisTemplate.boundValueOps(key).set(value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 获取String或者Object
     */
    @Override
    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取所有的Sting类型的缓存
     */
    @Override
    public Map<String, Object> getAllString() {
        Set<String> keySet = getAllKeys();
        Map<String, Object> map = new HashMap<>();
        for (String key : keySet) {
            if (getType(key) == DataType.STRING) {
                map.put(key, get(key));
            }
        }
        return map;
    }

    /**
     * 获取String或者Object
     */
    @Override
    public List<Object> get(String... keys) {
        List<Object> list = new ArrayList<>();
        for (String key : keys) {
            list.add(get(key));
        }
        return list;
    }

    /**
     * 获取String或者Object,根据正则表达式匹配
     */
    @Override
    public List<Object> getByRegular(String regKey) {
        Set<String> keySet = getAllKeys();
        List<Object> objectList = new ArrayList<>();
        for (String key : keySet) {
            if (Pattern.compile(regKey).matcher(key).matches() && getType(key) == DataType.STRING) {
                objectList.add(get(key));
            }
        }
        return objectList;
    }

    /**
     * 删除缓存
     */
    @Override
    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除缓存
     */
    @Override
    public void remove(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                remove(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 模糊移除,支持*号等匹配移除
     */
    @Override
    public void removePattern(String pattern) {
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    /**
     * 模糊移除,支持*号等匹配移除
     */
    @Override
    public void removePatterns(String... patterns) {
        for (String pattern : patterns) {
            removePattern(pattern);
        }
    }

    /**
     * 根据正则表达式来移除缓存
     */
    @Override
    public void removeByRegular(String regKey) {
        Set<String> keySet = getAllKeys();
        for (String key : keySet) {
            if (Pattern.compile(regKey).matcher(key).matches()) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * 根据正则表达式来移除缓存
     */
    @Override
    public void removeByRegular(String... regKeys) {
        for (String regKey : regKeys) {
            removePattern(regKey);
        }
    }
    //==========String Start==========

    //==========List Start==========

    /**
     * 向List中添加值,并返回在list中的下标
     */
    @Override
    public Long addList(String key, Object obj) {
        return redisTemplate.boundListOps(key).rightPush(obj);
    }

    /**
     * 向List中添加值,并返回在list中的下标
     */
    @Override
    public Long addList(String key, Object... obj) {
        return redisTemplate.boundListOps(key).rightPushAll(obj);
    }

    /**
     * 向List中合并另一个List
     */
    @Override
    public void addList(String key, List<Object> objectList) {
        for (Object obj : objectList) {
            addList(key, obj);
        }
    }

    /**
     * 获取所有的List类型缓存
     */
    @Override
    public Map<String, List<Object>> getAllList() {
        Set<String> keySet = getAllKeys();
        Map<String, List<Object>> map = new HashMap<>();
        for (String key : keySet) {
            if (getType(key) == DataType.LIST) {
                map.put(key, getList(key));
            }
        }
        return map;
    }

    /**
     * 获取List
     */
    @Override
    public List<Object> getList(String key) {
        return redisTemplate.boundListOps(key).range(0, getListSize(key));
    }

    /**
     * 获取索引在start-end区间的List
     */
    @Override
    public List<Object> getList(String key, Long start, Long end) {
        return redisTemplate.boundListOps(key).range(start, end);
    }

    /**
     * 获取List中元素的个数
     */
    @Override
    public Long getListSize(String key) {
        return redisTemplate.boundListOps(key).size();
    }


    /**
     * 移除List中的值,并返回移除量
     */
    @Override
    public Long removeListValue(String key, Object object) {
        return redisTemplate.boundListOps(key).remove(0, object);
    }

    /**
     * 移除List中的值,并返回移除量
     */

    @Override
    public Long removeListValue(String key, Object... objects) {
        Long count = 0L;
        for (Object object : objects) {
            count += removeListValue(key, object);
        }
        return count;
    }
    //==========List end==========

    //==========Set Start===========

    /**
     * 向Set中添加值
     */
    @Override
    public void addSet(String key, Object... obj) {
        redisTemplate.boundSetOps(key).add(obj);
    }

    /**
     * 设置Set过期时间 - 单位s
     */
    @Override
    public Boolean setExpireTimeForSet(String key, Long time) {
        return redisTemplate.boundSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    /**
     * 获取所有的Set类型的缓存
     */
    @Override
    public Map<String, Set<Object>> getAllSet() {
        Set<String> keySet = getAllKeys();
        Map<String, Set<Object>> map = new HashMap<>();
        for (String key : keySet) {
            if (getType(key) == DataType.SET) {
                map.put(key, getSet(key));
            }
        }
        return map;
    }

    /**
     * 获得Set
     */
    @Override
    public Set<Object> getSet(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    /**
     * 获取Set的元素个数
     */
    @Override
    public Long getSetSize(String key) {
        return redisTemplate.boundSetOps(key).size();
    }

    /**
     * 判断Set中是否存在这个值
     */
    @Override
    public Boolean hasSetValue(String key, Object obj) {
        Boolean isMember = null;
        int t = 0;
        while (true) {
            try {
                isMember = redisTemplate.boundSetOps(key).isMember(obj);
                break;
            } catch (Exception e) {
                logger.error("key[{}],obj[{}]判断Set中的值是否存在失败,异常信息:{}", key, obj, e.getMessage());
                t++;
            }
            if (t > TIMES) {
                break;
            }
        }
        logger.info("key[{}],obj[{}]是否存在,isMember:{}", key, obj, isMember);
        return isMember;
    }

    /**
     * 获取Set的并集
     */
    @Override
    public Set<Object> getSetUnion(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).union(otherKey);
    }

    /**
     * 获取Set的并集
     */
    @Override
    public Set<Object> getSetUnion(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).union(set);
    }

    /**
     * 获取Set的交集
     */
    @Override
    public Set<Object> getSetIntersect(String key, String otherKey) {
        return redisTemplate.boundSetOps(key).intersect(otherKey);
    }

    /**
     * 获取Set的交集
     */
    @Override
    public Set<Object> getSetIntersect(String key, Set<Object> set) {
        return redisTemplate.boundSetOps(key).intersect(set);
    }

    /**
     * 移除Set中的值
     */
    @Override
    public Long removeSetValue(String key, Object obj) {
        return redisTemplate.boundSetOps(key).remove(obj);
    }

    /**
     * 移除Set中的值
     */
    @Override
    public Long removeSetValue(String key, Object... obj) {
        if (obj != null && obj.length > 0) {
            return redisTemplate.boundSetOps(key).remove(obj);
        }
        return 0L;
    }
    //==========Set End=============

    //==========ZSet Start==========

    /**
     * 向ZSet中添加带score信息的值
     */
    @Override
    public Boolean addZSet(String key, Double score, Object value) {
        return redisTemplate.boundZSetOps(key).add(value, score);
    }

    /**
     * 向ZSet中添加带score信息的值
     */
    @Override
    public Boolean addZSet(String key, Double[] score, Object[] value) {
        if (score.length != value.length) {
            return false;
        }
        for (int i = 0; i < score.length; i++) {
            if (!addZSet(key, score[i], value[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向ZSet中添加有序集合
     */
    @Override
    public Long addZSet(String key, TreeSet<Object> value) {
        return redisTemplate.boundZSetOps(key).add(value);
    }


    /**
     * 将key对应的集合和key1对应的集合,合并到key2中
     * 如果分数相同都会保留
     * 原来key2的值会被覆盖
     */
    @Override
    public void setZSetUnionAndStore(String key, String key1, String key2) {
        redisTemplate.boundZSetOps(key).unionAndStore(key1, key2);
    }

    /**
     * 设置ZSet过期时间 - 单位s
     */
    @Override
    public Boolean setExpireTimeForZSet(String key, Long time) {
        return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    /**
     * 获取所有的正序排列不带score信息的ZSet
     */
    @Override
    public Map<String, Set<Object>> getAllZSetRange() {
        Set<String> keySet = getAllKeys();
        Map<String, Set<Object>> map = new HashMap<>();
        for (String key : keySet) {
            if (getType(key) == DataType.ZSET) {
                map.put(key, getZSetRange(key));
            }
        }
        return map;
    }

    /**
     * 获取所有的倒叙排列不带score信息的ZSet
     */

    @Override
    public Map<String, Set<Object>> getAllZSetReverseRange() {
        Set<String> keySet = getAllKeys();
        Map<String, Set<Object>> map = new HashMap<>();
        for (String key : keySet) {
            if (getType(key) == DataType.ZSET) {
                map.put(key, getZSetReverseRange(key));
            }
        }
        return map;
    }

    /**
     * 获取正序排列的ZSet
     */
    @Override
    public Set<Object> getZSetRange(String key) {
        return getZSetRange(key, 0L, getZSetSize(key));
    }

    /**
     * 获取索引在start-end区间正序排列的ZSet
     */
    @Override
    public Set<Object> getZSetRange(String key, Long start, Long end) {
        return redisTemplate.boundZSetOps(key).range(start, end);
    }

    /**
     * 获取倒序排列的ZSet
     */
    @Override
    public Set<Object> getZSetReverseRange(String key) {
        return getZSetReverseRange(key, 0L, getZSetSize(key));
    }

    /**
     * 获取索引在start-end区间倒序排列的ZSet
     */
    @Override
    public Set<Object> getZSetReverseRange(String key, Long start, Long end) {
        return redisTemplate.boundZSetOps(key).reverseRange(start, end);
    }

    /**
     * 获取分数在min-max区间正序排列的ZSet
     */
    @Override
    public Set<Object> getZSetRangeByScore(String key, Double min, Double max) {
        return redisTemplate.boundZSetOps(key).rangeByScore(min, max);
    }

    /**
     * 获取分数在min-max区间倒序排列的ZSet
     */
    @Override
    public Set<Object> getZSetReverseRangeByScore(String key, Double min, Double max) {
        return redisTemplate.boundZSetOps(key).reverseRangeByScore(min, max);
    }

    /**
     * 获取索引在start-end区间带score信息正序排列的ZSet
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key, Long start, Long end) {
        return redisTemplate.boundZSetOps(key).rangeWithScores(start, end);
    }

    /**
     * 获取索引在start-end区间带score信息倒序排列的ZSet
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key, Long start, Long end) {
        return redisTemplate.boundZSetOps(key).reverseRangeWithScores(start, end);
    }

    /**
     * 获取带score信息正序排列的ZSet
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRangeWithScores(String key) {
        return getZSetRangeWithScores(key, 0L, getZSetSize(key));
    }

    /**
     * 获取带score信息倒序排列的ZSet
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetReverseRangeWithScores(String key) {
        return getZSetReverseRangeWithScores(key, 0L, getZSetSize(key));
    }

    /**
     * 获取ZSet中分数在min-max区间的元素个数
     */
    @Override
    public Long getZSetCountSize(String key, Double min, Double max) {
        return redisTemplate.boundZSetOps(key).count(min, max);
    }

    /**
     * 获取ZSet的元素个数
     */
    @Override
    public Long getZSetSize(String key) {
        return redisTemplate.boundZSetOps(key).size();
    }

    /**
     * 获取ZSet中value对应的分数
     */
    @Override
    public Double getZSetScore(String key, Object value) {
        return redisTemplate.boundZSetOps(key).score(value);
    }

    /**
     * 元素分数增加,delta是增量
     */
    @Override
    public Double incrementZSetScore(String key, Object value, Double delta) {
        return redisTemplate.boundZSetOps(key).incrementScore(value, delta);
    }

    /**
     * 移除ZSet
     */
    @Override
    public void removeZSet(String key) {
        removeZSetRange(key, 0L, getZSetSize(key));
    }

    /**
     * 移除ZSet中对应的value
     */
    @Override
    public Long removeZSetValue(String key, Object... value) {
        return redisTemplate.boundZSetOps(key).remove(value);
    }

    /**
     * 删除ZSet中索引在start-end区间的值
     */
    @Override
    public void removeZSetRange(String key, Long start, Long end) {
        redisTemplate.boundZSetOps(key).removeRange(start, end);
    }

    /**
     * 通过分数删除ZSet中的值
     */
    @Override
    public void removeZSetRangeByScore(String key, Double min, Double max) {
        redisTemplate.boundZSetOps(key).removeRangeByScore(min, max);
    }
    //==========ZSet End============

    //==========Map start===========

    /**
     * 添加Map
     */
    @Override
    public void addMap(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    /**
     * 向Map中添加值
     */
    @Override
    public void addMap(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    /**
     * 向Map中添加值
     */
    @Override
    public void addMap(String key, String field, Object value, Long time) {
        redisTemplate.boundHashOps(key).put(field, value);
        redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
    }

    /**
     * 获取所有的Map类型缓存
     */
    @Override
    public Map<String, Map<String, Object>> getAllMap() {
        Set<String> keySet = getAllKeys();
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (String k : keySet) {
            if (getType(k) == DataType.HASH) {
                map.put(k, getMap(k));
            }
        }
        return map;
    }

    /**
     * 获取Map
     */
    @Override
    public Map<String, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 判断Map中是否有给定的key
     */
    @Override
    public Boolean hasMapKey(String key, String field) {
        return redisTemplate.boundHashOps(key).hasKey(field);
    }

    /**
     * 获取Map的所有value
     */
    @Override
    public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

    /**
     * 获取Map的所有key
     */
    @Override
    public Set<Object> getMapFieldKey(String key) {
        return redisTemplate.boundHashOps(key).keys();
    }

    /**
     * 获取Map中的key的个数
     */
    @Override
    public Long getMapSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }


    /**
     * 获取Map中给定的key所对应的value
     */
    @Override
    public <T> T getMapField(String key, String field) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }


    /**
     * 删除Map中给定的key所对应的value
     */
    @Override
    public void removeMapField(String key, Object... field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

    /**
     * 根据正则表达式来移除Map中的field
     */
    @Override
    public void removeMapFieldByRegular(String key, String regKey) {
        Map<String, Object> map = getMap(key);
        Set<String> keySet = map.keySet();
        for (String field : keySet) {
            if (Pattern.compile(regKey).matcher(field).matches()) {
                redisTemplate.boundHashOps(key).delete(field);
            }
        }
    }

    /**
     * 根据正则表达式来移除 Map中的field
     */
    @Override
    public void removeMapFieldByRegular(String key, String... regKeys) {
        for (String regKey : regKeys) {
            removeMapFieldByRegular(key, regKey);
        }
    }
    //==========Map End============

    public void removeByPattern(String pattern) {
        Optional.ofNullable(keys(pattern)).ifPresent(keys -> keys.forEach(this::remove));
    }

    public void sendChannelMess(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);

    }

}
