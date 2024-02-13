package com.demo.ratelimiter;

import com.demo.ratelimiter.inputs.Request;
import com.demo.ratelimiter.inputs.Rules;
import com.demo.ratelimiter.ratelimit.RateLimitHandler;
import com.demo.ratelimiter.ratelimit.RateLimiter;
import com.demo.ratelimiter.ratelimit.SlidingWindowCounter;
import com.demo.ratelimiter.redis.RedisHandler;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] arts) throws InterruptedException {
        Rules rules=new Rules();
        rules.setRequest_per_unit(5);
        System.out.println(Duration.ofMinutes(1).toMillis());
        rules.setUnit(Duration.ofMinutes(1));
        rules.setDomain("testUrl");
        RateLimiter rm=new SlidingWindowCounter();
        RateLimitHandler rateLimitHandler=new RateLimitHandler();
        rateLimitHandler.init(rules,rm);

        Request request=new Request();
        request.setRequestId("101");
        request.setUrl("testUrl");
        boolean result=rateLimitHandler.filter(rm, request);
        System.out.println(result);

    }
}
