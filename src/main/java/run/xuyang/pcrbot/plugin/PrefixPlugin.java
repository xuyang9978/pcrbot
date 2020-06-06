package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.stereotype.Component;

/**
 * 设置机器人指令的前缀
 *
 * @author XuYang
 * @date 2020/6/2 21:55
 */
@Component
public class PrefixPlugin extends CQPlugin {

    /**
     * 机器人指令的前缀，没有这个前缀无法触发机器人
     */
    private static final String prefix = "#";


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage().trim();

        // 如果群聊中接收到的消息满足指定的前缀开头
        if (msg.startsWith(prefix)) {
            event.setMessage(msg.substring(prefix.length()).trim());
            return MESSAGE_IGNORE;
        } else {
            // 后面的插件不再执行
            return MESSAGE_BLOCK;
        }
    }
}
