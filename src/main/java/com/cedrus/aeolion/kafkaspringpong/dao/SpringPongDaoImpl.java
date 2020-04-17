package com.cedrus.aeolion.kafkaspringpong.dao;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.cedrus.aeolion.kafkaspringpong.util.SpringPongRowMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class SpringPongDaoImpl implements SpringPongDao {
    private final JdbcTemplate jdbcTemplate;
    private final SpringPongRowMapper springPongRowMapper;

    @Autowired
    public SpringPongDaoImpl(DataSource ds, SpringPongRowMapper springPongRowMapper) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.springPongRowMapper = springPongRowMapper;
    }

    public SpringPongBall getBallById(String id) {
        final String sql = "SELECT * FROM spring_pong WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, springPongRowMapper);
    }

    public List<SpringPongBall> getAll() {
        final String sql = "SELECT * FROM spring_pong";
        return jdbcTemplate.query(sql, springPongRowMapper);
    }

    public int createBall(@NotNull SpringPongBall spb) {
        final String sql = "INSERT INTO spring_pong (id, color, target) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, spb.getId(), spb.getColor(), spb.getTarget());
    }

    public int updateBall(@NotNull SpringPongBall spb) {
        final String sql = "UPDATE spring_pong SET target = ?, color = ? WHERE ballId = ?";
        return jdbcTemplate.update(sql, spb.getTarget(), spb.getColor(), spb.getId());
    }

    public int deleteBall(@NotNull SpringPongBall spb) {
        final String sql = "DELETE FROM spring_pong WHERE ballId = ?";
        return jdbcTemplate.update(sql, spb.getId());
    }
}