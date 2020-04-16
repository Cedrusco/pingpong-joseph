package com.cedrus.aeolion.kafkaspringpong.dao;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SpringPongDaoImpl {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpringPongDaoImpl(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public SpringPongBall getBallById(String id) {
        String sql = "SELECT * FROM spring_pong WHERE ID = ?";
//         TODO: look into row callback handlers, result set extractors, and/or row mappers
//        return jdbcTemplate.query();
    }

    public int createBall(SpringPongBall spb) {
        String sql = "INSERT INTO spring_pong (id, color, target) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, spb.getId(), spb.getColor(), spb.getTarget());
    }

    public int updateBall(SpringPongBall spb) {
        String sql = "UPDATE spring_pong SET target = ?, color = ? WHERE id = ?";
        return jdbcTemplate.update(sql, spb.getId(), spb.getColor(), spb.getId());
    }

    public int deleteBall(SpringPongBall spb) {
        String sql = "DELETE FROM spring_pong WHERE id = ?";
        return jdbcTemplate.update(sql, spb.getId());
    }
}