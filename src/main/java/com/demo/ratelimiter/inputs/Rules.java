package com.demo.ratelimiter.inputs;

import java.sql.Time;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Rules {
    private String domain;
    private Duration unit;
    private int request_per_unit;
    public Rules(){}

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Duration getUnit() {
        return unit;
    }

    public void setUnit(Duration unit) {
        this.unit = unit;
    }

    public int getRequest_per_unit() {
        return request_per_unit;
    }

    public void setRequest_per_unit(int request_per_unit) {
        this.request_per_unit = request_per_unit;
    }
}
