package com.demo.ratelimiter.redis;

import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;

public class RedisHandler {

    private static final RedisHandler redisHandler = new RedisHandler();
    private static JedisPool jedisPool;
    private static Jedis jedis;

    private static Config config ;
    private RedissonClient redisson;

    private RedisHandler(){
        jedisPool =new JedisPool("localhost", 6379);
        jedis = jedisPool.getResource();
        config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        redisson = Redisson.create(config);
    }

    public static RedisHandler getRedisHandler(){
        return redisHandler;
    }

    public void putKey(String key,String value){
        jedis.set(key,value);
    }

    public String getKey(String key){

        return jedis.get(key);

    }

    public void addToSortedList(String key,Long rank,String value){

        jedis.zadd(key,rank,value);
        System.out.println(jedis.zrange(key,0,rank));
    }

    public long getListSize(String name){
        return jedis.zcard(name);
    }

    public void removeList(String name,long min,long max){
        jedis.zremrangeByScore(name,min,max);
    }

    public boolean scriptEval(String scriptEval,String name,Object[] keys,Object[] values){
        RScript script = redisson.getScript(StringCodec.INSTANCE);
        Boolean isAllowed = script.eval(name, RScript.Mode.READ_WRITE,scriptEval, RScript.ReturnType.BOOLEAN, Arrays.asList(keys),values);
        return isAllowed;
    }
}
