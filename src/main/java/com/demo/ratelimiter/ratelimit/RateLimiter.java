package com.demo.ratelimiter.ratelimit;

import com.demo.ratelimiter.inputs.Request;
import com.demo.ratelimiter.inputs.Rules;

public interface RateLimiter {

     void initRules(Rules rules);
     boolean validate(Request request);
}
