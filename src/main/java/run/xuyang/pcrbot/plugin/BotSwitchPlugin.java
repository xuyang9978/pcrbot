package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.constants.BotConstants;
import run.xuyang.pcrbot.entity.BotStatus;
import run.xuyang.pcrbot.service.BotStatusService;

/**
 * 开启或关闭机器人
 *
 * @author XuYang
 * @date 2020/6/3 14:38
 */
@Component
public class BotSwitchPlugin extends CQPlugin {

    private final BotStatusService botStatusService;

    public BotSwitchPlugin(BotStatusService botStatusService) {
        this.botStatusService = botStatusService;
    }


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage();
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        String permission = event.getSender().getRole();


        BotStatus botStatus = botStatusService.findGroupByGroupID(groupID);
        if (msg.contains("开启机器人")) {
            if ("member".equals(permission)) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
            } else {
                if (null != botStatus) {
                    if (botStatus.getGroupBotStatus() != BotConstants.OPEN) {
                        botStatusService.updateBotByGroupID(groupID, BotConstants.OPEN);
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "开启成功！", false);
                    } else {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "已经开启啦！不要重复开启哟~", false);
                    }
                } else {
                    botStatusService.addBotToGroup(groupID, BotConstants.OPEN);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "开启成功！", false);
                }
            }
        } else if (msg.contains("关闭机器人")) {
            if ("member".equals(permission)) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
            } else {
                if (null == botStatus) {
                    botStatusService.addBotToGroup(groupID, BotConstants.CLOSE);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "关闭成功！", false);
                } else {
                    if (botStatus.getGroupBotStatus() == BotConstants.CLOSE) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "已经关闭啦！不要重复关闭哟~", false);
                    } else {
                        botStatusService.updateBotByGroupID(groupID, BotConstants.CLOSE);
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "关闭成功！", false);
                    }
                }
            }
        }

        // 处理其余指令
        // 再次从数据库中读取当前群的机器人状态
        if (botStatusService.findGroupByGroupID(groupID).getGroupBotStatus() == BotConstants.OPEN) {
            return MESSAGE_IGNORE;
        } else {
            return MESSAGE_BLOCK;
        }
    }
}
