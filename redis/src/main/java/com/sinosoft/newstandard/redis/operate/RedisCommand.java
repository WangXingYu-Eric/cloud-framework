package com.sinosoft.newstandard.redis.operate;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
@SuppressWarnings({"unchecked", "ConstantConditions"})
public interface RedisCommand<K, V> {

    /**
     * 判断缓存中key是否存在
     */
    Boolean exists(K key);

    /**
     * 处理事务时锁定key
     */
    void watch(String key);

    /**
     * 基于时间戳的权重值
     */
    Double getCreateTimeScore(Long date);

    /**
     * 获取缓存的类型
     */
    DataType getType(K key);

    /**
     * 获取Redis中所有的键的key
     */
    Set<K> getAllKeys();

    /**
     * 获取Redis符合条件的key
     */
    Set<K> keys(String keyPattern);

    /**
     * 设置过期时间 - 单位s
     */
    Boolean setExpireTime(K key, Long expireTime);

    /**
     * 修改key,如果不存在key或者没有修改成功则返回false
     */
    Boolean renameIfAbsent(String oldKey, String newKey);

    //==========String Start==========

    /**
     * 添加缓存
     */
    void set(K key, V value);

    Boolean setNX(String key, Object value);

    /**
     * 添加缓存,并设置过期时间 - 单位s
     */
    void set(K key, V value, Long expireTime);

    /**
     * 获取String或者Object
     */
    V get(final K key);

    /**
     * 获取所有的Sting类型的缓存
     */
    Map<K, V> getAllString();

    /**
     * 获取String或者Object
     */
    List<V> get(final K... key);

    /**
     * 获取String或者Object,根据正则表达式匹配
     */
    List<Object> getByRegular(final K regKey);

    /**
     * 删除缓存
     */
    void remove(final K key);

    /**
     * 批量删除缓存
     */
    void remove(final K... keys);

    /**
     * 模糊移除,支持*号等匹配移除
     */
    void removePattern(K pattern);

    /**
     * 模糊移除,支持*号等匹配移除
     */
    void removePatterns(K... patterns);

    /**
     * 根据正则表达式来移除缓存
     */
    void removeByRegular(String regKey);

    /**
     * 根据正则表达式来移除缓存
     */
    void removeByRegular(String... regKeys);
    //==========String End===========

    //==========List Start==========

    /**
     * 向List中添加值,并返回在list中的下标
     */
    Long addList(K key, V obj);

    /**
     * 向List中添加值,并返回在list中的下标
     */
    Long addList(K key, V... obj);

    /**
     * 向List中合并另一个List
     */
    void addList(K key, List<V> objectList);

    /**
     * 获取所有的List类型缓存
     */
    Map<K, List<V>> getAllList();

    /**
     * 获取List
     */
    List<V> getList(K key);

    /**
     * 获取索引在start-end区间的List
     */
    List<V> getList(K key, Long start, Long end);

    /**
     * 获取List中元素的个数
     */
    Long getListSize(K key);

    /**
     * 移除List中的值,并返回移除量
     */
    Long removeListValue(K key, V object);

    /**
     * 移除List中的值,并返回移除量
     */
    Long removeListValue(K key, V... object);
    //==========List end==========

    //==========Set Start===========

    /**
     * 向Set中添加值
     */
    void addSet(K key, V... obj);

    /**
     * 设置Set过期时间 - 单位s
     */
    Boolean setExpireTimeForSet(K key, Long time);

    /**
     * 获取所有的Set类型的缓存
     */
    Map<K, Set<V>> getAllSet();

    /**
     * 获得Set
     */
    Set<V> getSet(K key);

    /**
     * 获取Set的元素个数
     */
    Long getSetSize(K key);

    /**
     * 判断Set中是否存在这个值
     */
    Boolean hasSetValue(K key, V obj);

    /**
     * 获取Set的并集
     */
    Set<V> getSetUnion(K key, K otherKey);

    /**
     * 获取Set的并集
     */
    Set<V> getSetUnion(K key, Set<Object> set);

    /**
     * 获取Set的交集
     */
    Set<V> getSetIntersect(K key, K otherKey);

    /**
     * 获取Set的交集
     */
    Set<V> getSetIntersect(K key, Set<Object> set);

    /**
     * 移除Set中的值
     */
    Long removeSetValue(K key, V obj);

    /**
     * 移除Set中的值
     */
    Long removeSetValue(K key, V... obj);
    //==========Set End=============

    //==========ZSet Start==========

    /**
     * 向ZSet中添加带score信息的值
     */
    Boolean addZSet(String key, Double score, Object value);

    /**
     * 向ZSet中添加带score信息的值
     */
    Boolean addZSet(K key, Double[] score, Object[] value);

    /**
     * 向ZSet中添加有序集合
     */
    Long addZSet(K key, TreeSet<V> value);

    /**
     * 将key对应的集合和key1对应的集合,合并到key2中
     * 如果分数相同都会保留
     * 原来key2的值会被覆盖
     */
    void setZSetUnionAndStore(String key, String key1, String key2);

    /**
     * 设置ZSet过期时间 - 单位s
     */
    Boolean setExpireTimeForZSet(K key, Long time);

    /**
     * 获取所有的正序排列不带score信息的ZSet
     */
    Map<K, Set<V>> getAllZSetRange();

    /**
     * 获取所有的倒叙排列不带score信息的ZSet
     */
    Map<K, Set<V>> getAllZSetReverseRange();

    /**
     * 获取正序排列的ZSet
     */
    <T> T getZSetRange(K key);

    /**
     * 获取索引在start-end区间正序排列的ZSet
     */
    <T> T getZSetRange(K key, Long start, Long end);

    /**
     * 获取倒序排列的ZSet
     */
    Set<Object> getZSetReverseRange(K key);

    /**
     * 获取索引在start-end区间倒序排列的ZSet
     */
    Set<V> getZSetReverseRange(K key, Long start, Long end);

    /**
     * 获取分数在min-max区间正序排列的ZSet
     */
    Set<V> getZSetRangeByScore(String key, Double min, Double max);

    /**
     * 获取分数在min-max区间倒序排列的ZSet
     */
    Set<V> getZSetReverseRangeByScore(String key, Double min, Double max);

    /**
     * 获取索引在start-end区间带score信息正序排列的ZSet
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key, Long start, Long end);

    /**
     * 获取索引在start-end区间带score信息倒序排列的ZSet
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key, Long start, Long end);

    /**
     * 获取带score信息正序排列的ZSet
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key);

    /**
     * 获取带score信息倒序排列的ZSet
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key);

    /**
     * 获取ZSet中分数在min-max区间的元素个数
     */
    Long getZSetCountSize(K key, Double min, Double max);

    /**
     * 获取ZSet的元素个数
     */
    Long getZSetSize(K key);

    /**
     * 获取ZSet中value对应的分数
     */
    Double getZSetScore(K key, V value);

    /**
     * 元素分数增加,delta是增量
     */
    Double incrementZSetScore(K key, V value, Double delta);

    /**
     * 移除ZSet
     */
    void removeZSet(K key);

    /**
     * 移除ZSet中对应的value
     */
    Long removeZSetValue(K key, V... value);

    /**
     * 删除ZSet中索引在start-end区间的值
     */
    void removeZSetRange(K key, Long start, Long end);

    /**
     * 通过分数删除ZSet中的值
     */
    void removeZSetRangeByScore(String key, Double min, Double max);
    //==========ZSet End============

    //==========Map start===========

    /**
     * 添加Map
     */
    void addMap(K key, Map<K, V> map);

    /**
     * 向Map中添加值
     */
    void addMap(K key, K field, Object value);

    /**
     * 向Map中添加值
     */
    void addMap(K key, K field, V value, Long time);

    /**
     * 获取所有的Map类型缓存
     */
    Map<K, Map<K, V>> getAllMap();

    /**
     * 获取Map
     */
    Map<K, V> getMap(K key);

    /**
     * 判断Map中是否有给定的key
     */
    Boolean hasMapKey(K key, K field);

    /**
     * 获取Map的所有value
     */
    List<V> getMapFieldValue(K key);

    /**
     * 获取Map的所有key
     */
    Set<V> getMapFieldKey(K key);

    /**
     * 获取Map中的key的个数
     */
    Long getMapSize(K key);

    /**
     * 获取Map中给定的key所对应的value
     */
    <T> T getMapField(K key, K field);

    /**
     * 删除Map中给定的key所对应的value
     */
    void removeMapField(K key, V... field);

    /**
     * 根据正则表达式来移除Map中的field
     */
    void removeMapFieldByRegular(K key, K regKey);

    /**
     * 根据正则表达式来移除 Map中的field
     */
    void removeMapFieldByRegular(K key, K... regKeys);
    //==========Map End============
}