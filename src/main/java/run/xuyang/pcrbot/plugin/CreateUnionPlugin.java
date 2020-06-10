package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.entity.Unions;
import run.xuyang.pcrbot.service.*;
import run.xuyang.pcrbot.util.StringUtils;

/**
 * 创建或解散公会
 *
 * @author XuYang
 * @date 2020/6/4 13:45
 */
@Component
public class CreateUnionPlugin extends CQPlugin {

    private final UnionsService unionsService;

    private final BattleLogService battleLogService;

    private final BossListService bossListService;

    private final MemberService memberService;

    private final OrderListService orderListService;

    private final TreeListService treeListService;

    private final BattlingListService battlingListService;


    public CreateUnionPlugin(UnionsService unionsService,
                             BattleLogService battleLogService,
                             BossListService bossListService,
                             MemberService memberService,
                             OrderListService orderListService,
                             TreeListService treeListService,
                             BattlingListService battlingListService) {
        this.unionsService = unionsService;
        this.battleLogService = battleLogService;
        this.bossListService = bossListService;
        this.memberService = memberService;
        this.orderListService = orderListService;
        this.treeListService = treeListService;
        this.battlingListService = battlingListService;
    }

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String[] msg = event.getMessage().split(" ");
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        String permission = event.getSender().getRole();
        String[] parameters = StringUtils.trimStrArr(msg);

        Unions unions = unionsService.findUnionByGroupID(groupID);
        if (parameters[0].contains("创建公会")) {
            if ("member".equals(permission)) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
            } else {
                if (parameters.length <= 1) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                } else {
                    if (null == unions) {
                        // 先删除上次公会战的所有数据
                        if (0 != battlingListService.findBattlingListByGroupID(groupID).size()) {
                            battlingListService.deleteAllBattlingListByGroupID(groupID);
                        }
                        if (0 != battleLogService.findBattleLogByGroupID(groupID).size()) {
                            battleLogService.deleteAllByGroupID(groupID);
                        }
                        if (null != bossListService.findBossListByGroupID(groupID)) {
                            bossListService.deleteAllByGroupID(groupID);
                        }
                        if (0 != memberService.findMemberByGroupID(groupID).size()) {
                            memberService.deleteAllByGroupID(groupID);
                        }
                        if (0 != orderListService.findAllOrderListByGroup(groupID).size()) {
                            orderListService.deleteAllByGroupID(groupID);
                        }
                        if (0 != treeListService.findAllTreeListByGroupID(groupID).size()) {
                            treeListService.deleteAllByGroupID(groupID);
                        }
                        unionsService.addUnion(parameters[1], groupID);
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "创建公会成功！", false);
                    } else {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "该群公会已经存在啦！", false);
                    }
                }
            }
        } else if (parameters[0].contains("结束会战")) {
            if ("member".equals(permission)) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
            } else {
                if (null == unions) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "公会不存在，请先创建公会！", false);
                } else {
                    // 删除公会记录，其余数据在下次创建公会时清除
                    unionsService.deleteUnion(groupID);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "此次会战已成功关闭，公会已自动解散，数据保留直到下次创建公会时！", false);
                }
            }
        }

        // 处理其余指令
        // 再次从数据库中读取公会状态
        // 如果公会还没创建，那么后面与此次公会战相关的命令就不再处理了
//        if (null == unionsService.findUnionByGroupID(groupID)) {
//            return MESSAGE_BLOCK;
//        } else {
//            return MESSAGE_IGNORE;
//        }
        // 直接放行
        return MESSAGE_IGNORE;
    }
}
