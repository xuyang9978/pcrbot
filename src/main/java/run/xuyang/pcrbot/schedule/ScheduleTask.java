package run.xuyang.pcrbot.schedule;

import net.lz1998.cq.CQGlobal;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author XuYang
 * @date 2020/6/10 14:29
 */
@Component
public class ScheduleTask {

    /**
     * 每天0 6 12 18点提醒购买经验药水(国服时间)
     */
    @Scheduled(cron = "0 0 0,6,12,18 * * ? ", zone = "Asia/Shanghai")
    public void buyExMedicine() {
        CoolQ cq = CQGlobal.robots.get(3134778162L);
        cq.sendGroupMsg(1056321224L, "我是提醒买药小助手,大家和我一起每天买满四次药吧!", true);
    }
}
