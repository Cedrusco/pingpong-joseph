package com.cedrus.aeolion.kafkaspringpong.util;

import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.cedrus.aeolion.kafkaspringpong.model.Target;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SpringPongRowMapper implements RowMapper<SpringPongBall> {

    @Override
    public final SpringPongBall mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpringPongBall spb = new SpringPongBall();

        spb.setId(rs.getString("id"));
        spb.setTarget(Target.valueOf(rs.getString("target")));
        spb.setColor(rs.getString("color"));

        return spb;
    }
}
