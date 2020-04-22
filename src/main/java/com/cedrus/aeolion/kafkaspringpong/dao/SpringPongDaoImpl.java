package com.cedrus.aeolion.kafkaspringpong.dao;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.cedrus.aeolion.kafkaspringpong.util.SpringPongRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class SpringPongDaoImpl implements SpringPongDao {
    private final JdbcTemplate jdbcTemplate;
    private final SpringPongRowMapper springPongRowMapper;

    @Autowired
    public SpringPongDaoImpl(DataSource ds, SpringPongRowMapper springPongRowMapper) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.springPongRowMapper = springPongRowMapper;
    }

    public SpringPongBall getBallById(String id) {
        final String sql = "SELECT * FROM spring_pong WHERE ballId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { id }, springPongRowMapper);
    }

    public List<SpringPongBall> getAll() {
        final String sql = "SELECT * FROM spring_pong";
        return jdbcTemplate.query(sql, springPongRowMapper);
    }

    public int createBall(SpringPongBall spb) {
        final String sql = "INSERT INTO spring_pong (ballId, color, target) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, spb.getId(), spb.getColor(), spb.getTarget().toString());
    }

    public int updateBall(SpringPongBall spb) {
        final String sql = "UPDATE spring_pong SET target = ?, color = ? WHERE ballId = ?";
        return jdbcTemplate.update(sql, spb.getTarget().toString(), spb.getColor(), spb.getId());
    }

    public int deleteBall(SpringPongBall spb) {
        final String sql = "DELETE FROM spring_pong WHERE id = ?";
        return jdbcTemplate.update(sql, spb.getId());
    }
}
