package com.demo.ratelimiter.ratelimit;

import com.demo.ratelimiter.inputs.Request;
import com.demo.ratelimiter.inputs.Rules;

public class RateLimitHandler {

    public void init(Rules rules,RateLimiter limiter){
        limiter.initRules(rules);
    }

    public boolean filter(RateLimiter limiter,Request request){
            return limiter.validate(request);
    }
}
