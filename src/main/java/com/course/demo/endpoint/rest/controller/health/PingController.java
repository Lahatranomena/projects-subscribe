package com.course.demo.endpoint.rest.controller.health;

import com.course.demo.PojaGenerated;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PojaGenerated
@RestController
@AllArgsConstructor
public class PingController {

  public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
  public static final ResponseEntity<String> KO =
      new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);

  private final JdbcTemplate jdbcTemplate;

  @GetMapping("/ping")
  public String ping() {
    try {
      Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

      if (result != null && result == 1) {
        return "pong - Database connection successful!";
      } else {
        return "pong - Unexpected database response.";
      }
    } catch (Exception e) {
      return "pong - Database connection FAILED: " + e.getMessage();
    }
  }
}
