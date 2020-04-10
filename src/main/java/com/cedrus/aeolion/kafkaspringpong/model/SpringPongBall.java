package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpringPongBall {
    private String id;
    private String color;
    private Target target;

    public SpringPongBall(String id, String color, Target target) {
        this.id = id;
        this.color = color;
        this.target = target;
    }

    public void changeTarget() {
        if (this.target.equals(Target.PING)) {
            this.target = Target.PONG;
        } else {
            this.target = Target.PING;
        }
    }
}