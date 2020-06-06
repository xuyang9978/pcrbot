package run.xuyang.pcrbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("run.xuyang.pcrbot.mapper")
public class PcrBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcrBotApplication.class, args);
    }

}
