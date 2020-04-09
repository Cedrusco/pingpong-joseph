package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SpringPongBall {
    private String id;
    private String color;
    private Target target;

    public SpringPongBall(String id, String color, Target target) {
        this.id = id;
        this.color = color;
        this.target = target;
    }

    public SpringPongBall() { }

    public void changeTarget() {
        if (this.target.equals(Target.PING)) {
            this.target = Target.PONG;
        } else {
            this.target = Target.PING;
        }
        log.info("Target.PING is " + Target.PING);
        log.info("Target.PONG is " + Target.PONG);
        log.info("this.target is " + this.target);
    }
}