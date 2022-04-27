package org.choviwu.npcjava;

import org.choviwu.npcjava.plugin.App;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "org.choviwu.npcjava.plugin.mapper")
@SpringBootApplication
public class NpcJavaApplication implements ApplicationRunner {

    @Autowired
    private App app;

    public static void main(String[] args) {
        SpringApplication.run(NpcJavaApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
