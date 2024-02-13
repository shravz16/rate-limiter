package com.demo.ratelimiter.ratelimit;

import com.demo.ratelimiter.inputs.Request;
import com.demo.ratelimiter.inputs.Rules;
import com.demo.ratelimiter.redis.RedisHandler;


public class SlidingWindowCounter implements RateLimiter{

    private Rules rules;

    @Override
    public void initRules(Rules rules) {
        this.rules=rules;

    }

    @Override
    public boolean validate(Request request) {


        long timeMillis=System.currentTimeMillis();
        Object[] keys = new Object[] {rules.getDomain()+"_"+request.getUrl() };
        Object[] values = new Object[] {timeMillis,rules.getRequest_per_unit(),rules.getUnit().toMillis()};

        Boolean isAllowed =   RedisHandler.getRedisHandler().scriptEval(createLuaScript(),"RateLimit",keys,values);
        System.out.println("isAllowed::" +  ":" +isAllowed);
        return isAllowed;
    }





    private String createLuaScript(){
       return           "local key = KEYS[1]; " +
                        "local value = tonumber(ARGV[1]); " +
                        "local rank = tonumber(ARGV[1]); " +
                        "local allowed = tonumber(ARGV[2]);"+
                        "redis.call('ZADD', key,'NX',rank,value); " +
                        "local window=tonumber(ARGV[3]);"+
                        "redis.call('ZREMRANGEBYSCORE', key, 0, value-window);"+
                        "local currentSize=tonumber(redis.call('ZCARD',key));"+
                        "if (currentSize<= allowed) then "+
                        "return true;"+
                        "else " +
                        "return false; " +
                        "end";

    }


}
