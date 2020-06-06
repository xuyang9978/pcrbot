package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.service.BossListService;

/**
 * 开启会战
 *
 * @author XuYang
 * @date 2020/6/4 21:16
 */
@Component
public class OpenUnionBattlePlugin extends CQPlugin {

    private final BossListService bossListService;

    public OpenUnionBattlePlugin(BossListService bossListService) {
        this.bossListService = bossListService;
    }

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {

        String msg = event.getMessage();
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        String permission = event.getSender().getRole();

        if ("开启会战".equals(msg)) {
            if ("member".equals(permission)) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
            } else {
                // 判断该公会是否已经开启过了
                if (null == bossListService.findBossListByGroupID(groupID)) {
                    // 初始化boss列表
                    bossListService.addOneBossListByGroup(groupID);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "会战开启成功，尽情讨伐boss吧！", false);
                } else {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "此次会战已经开启，请不要重复开启！", false);
                }
            }
        }

        //再次判断会战是否开启了，以确定是否继续之后后面的命令
        if (null == bossListService.findBossListByGroupID(groupID)) {
            return MESSAGE_BLOCK;
        } else {
            return MESSAGE_IGNORE;
        }
    }
}
