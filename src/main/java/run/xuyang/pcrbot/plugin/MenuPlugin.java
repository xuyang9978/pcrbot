package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Objects;

/**
 * 展示功能菜单
 *
 * @author XuYang
 * @date 2020/6/3 14:33
 */
@Component
public class MenuPlugin extends CQPlugin {

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {

        String msg = event.getMessage();
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        InputStreamReader inputReader = null;
        BufferedReader bf = null;

        if ("菜单".equals(msg)) {
            // 展示菜单
            try {
                String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("./config/function.txt")).getPath();
                File file = new File(path);
                inputReader = new InputStreamReader(new FileInputStream(file));
                bf = new BufferedReader(inputReader);
                // 按行读取字符串
                StringBuilder rst = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    rst.append(line).append("\n");
                }
                // 发送群消息
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "\n"+ rst.toString(), false);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != bf) {
                        bf.close();
                    }
                    if (null != inputReader) {
                        inputReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return MESSAGE_IGNORE;
    }
}
