package run.xuyang.pcrbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@MapperScan("run.xuyang.pcrbot.mapper")
@EnableScheduling
public class PcrBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcrBotApplication.class, args);
    }

    /**
     * 用于解决定时任务报错问题
     *
     * @return taskScheduler
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("springboot task");
        return taskScheduler;
    }
}
