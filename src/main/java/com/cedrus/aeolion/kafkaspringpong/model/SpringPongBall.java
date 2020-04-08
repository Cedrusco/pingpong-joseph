package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;

@Data
public class SpringPongBall {
    private String id;
    private String color;
    private Target target;

    public void changeTarget() {
        if (target.equals(Target.PING)) {
            this.target = Target.PONG;
        } else {
            this.target = Target.PING;
        }
    }
}