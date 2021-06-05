package com.yqh.eurekaprovider;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author m1768
 * @Auther：yqh
 * @Date：2021/5/30
 * @Description：创建
 * @Version：1.0
 */

@Component
public class HealthStatusService implements HealthIndicator {

    private boolean status = true;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public Health health() {

        if (status) {
            return new Health.Builder().up().build();
        }

        return new Health.Builder().down().build();
    }

}
