package com.cedrus.aeolion.kafkaspringpong;

import com.cedrus.aeolion.kafkaspringpong.config.DBConfig;
import com.cedrus.aeolion.kafkaspringpong.dao.SpringPongDao;
import com.cedrus.aeolion.kafkaspringpong.dao.SpringPongDaoImpl;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.cedrus.aeolion.kafkaspringpong.model.Target;
//import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Random;

// TODO: slf4j is unrecognized, implement it in place of System.out.println

//@Slf4j
public class SpringPongDaoTest {

    final Target[] targets = Target.values();
    final String[] colors = {"red", "blue", "green", "purple", "yellow", "orange"};
    final Random random = new Random();

    @Test
    public final void testDao() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);

        final SpringPongDao springPongDao = context.getBean(SpringPongDaoImpl.class);

        System.out.println("Creating mock ping pong balls...");

        for (int i = 0; i < 10; i++) {
            final Target randomTarget = generateTarget();
            final String randomColor = generateColor();
            final SpringPongBall springPongBall = new SpringPongBall(Integer.toString(i), randomColor, randomTarget);

            springPongDao.createBall(springPongBall);
        }

        System.out.println("Mock balls created and inserted into database.");

        final List<SpringPongBall> initialSPBList = springPongDao.getAll();

        System.out.println("Initial ball list:");
        for (SpringPongBall springPongBall : initialSPBList) {
            System.out.println(springPongBall);
        }

        System.out.println("Retrieving ball with ID 4...");
        SpringPongBall springPongBall4 = springPongDao.getBallById("4");
        System.out.println(springPongBall4);
        System.out.println("Ball retrieved.");

        String springPongBall4InitColor = springPongBall4.getColor();

        assert springPongBall4.getId().equals("4");
        assert springPongBall4InitColor != null;
        assert springPongBall4.getTarget() != null;

        System.out.println("Updating ball with ID 4...");
        springPongBall4.setColor("white");
        springPongBall4.setTarget(Target.PONG);
        springPongDao.updateBall(springPongBall4);

        assert !springPongBall4InitColor.equals(springPongDao.getBallById("4"));
        System.out.println("Ball updated.");

        final List<SpringPongBall> modifiedSPBList = springPongDao.getAll();

        System.out.println("Beginning database cleanup...");
        for (SpringPongBall springPongBall : modifiedSPBList) {
            System.out.println("Deleting ball: " + springPongBall);
            springPongDao.deleteBall(springPongBall);
        }

        System.out.println("Database cleanup complete.");

        context.close();
    }

    private String generateColor() {
        return colors[random.nextInt(colors.length)];
    }

    private Target generateTarget() {
        return targets[random.nextInt(targets.length)];
    }
}
