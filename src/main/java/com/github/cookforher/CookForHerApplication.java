package com.github.cookforher;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CookForHerApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(CookForHerApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
  }
}