package com.cedrus.aeolion.kafkaspringpong.dao;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;

import java.util.List;

public interface SpringPongDao {
    SpringPongBall getBallById(String id);

    List<SpringPongBall> getAll();

    int createBall(SpringPongBall springPongBall);

    int updateBall(SpringPongBall springPongBall);

    int deleteBall(SpringPongBall springPongBall);
}