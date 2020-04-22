package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.model.*;
import com.cedrus.aeolion.kafkaspringpong.services.BallAdderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SpringPongController {
  private final BallAdderService ballAdder;

  @Autowired
  public SpringPongController(BallAdderService ballAdder) {
    this.ballAdder = ballAdder;
  }

  @PostMapping(value = "/ball")
  public ResponseEntity<SpringPongResponse> introduceBall(@RequestBody SpringPongRequest request) {
    log.info("Received request to add ball.");
    log.debug("Request = {}", request);

    final SpringPongResponse responseObj = new SpringPongResponse();

    if (request.getId() == null) {
      responseObj.setSuccessIndicator(false);
      responseObj.setResponseMessage("Ball ID not provided.");
      return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
    } else {
      responseObj.setSuccessIndicator(true);
      responseObj.setResponseMessage("OK!");
      addBall(request);
      return new ResponseEntity<>(responseObj, HttpStatus.OK);
    }
  }

  private ResponseEntity<SpringPongResponse> addBall(SpringPongRequest request) {
    final SpringPongResponse response = new SpringPongResponse(true, request.toString());

    try {
      final SpringPongBall ball =
          new SpringPongBall(request.getId(), request.getColor(), Target.PING);
      ballAdder.addBall(ball);

      response.setSuccessIndicator(true);
      response.setResponseMessage("OK!");

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Internal server error.");
      e.printStackTrace();

      response.setSuccessIndicator(false);
      response.setResponseMessage(e.getMessage());

      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
