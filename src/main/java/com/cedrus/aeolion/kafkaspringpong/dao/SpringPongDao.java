package com.cedrus.aeolion.kafkaspringpong.dao;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;

public interface SpringPongDao {
    SpringPongBall getBallById(String id);

    int createBall(SpringPongBall springPongBall);

    int updateBall(SpringPongBall springPongBall);

    int deleteBall(SpringPongBall springPongBall);
}