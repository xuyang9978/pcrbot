package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.junit.jupiter.api.Order;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.constants.BotConstants;
import run.xuyang.pcrbot.entity.*;
import run.xuyang.pcrbot.service.*;
import run.xuyang.pcrbot.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工会战相关命令
 *
 * @author XuYang
 * @date 2020/6/2 22:06
 */
@Component
public class UnionBattlePlugin extends CQPlugin {

    private final BotStatusService botStatusService;

    private final UnionsService unionsService;

    private final MemberService memberService;

    private final BossListService bossListService;

    private final BattleLogService battleLogService;

    private final OrderListService orderListService;

    private final TreeListService treeListService;

    private final BattlingListService battlingListService;

    public UnionBattlePlugin(BotStatusService botStatusService,
                             UnionsService unionsService,
                             MemberService memberService,
                             BossListService bossListService,
                             BattleLogService battleLogService,
                             OrderListService orderListService,
                             TreeListService treeListService,
                             BattlingListService battlingListService) {
        this.botStatusService = botStatusService;
        this.unionsService = unionsService;
        this.memberService = memberService;
        this.bossListService = bossListService;
        this.battleLogService = battleLogService;
        this.orderListService = orderListService;
        this.treeListService = treeListService;
        this.battlingListService = battlingListService;
    }


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String[] msg = event.getMessage().split(" ");
        String rawmsg = event.getRawMessage();

        switch (msg[0]) {
            case "菜单":
                showMenu(cq, event);
                break;
            case "修改公会":
                updateUnion(cq, event, msg);
                break;
            case "踢出公会":
                kickOutUnion(cq, event, rawmsg);
                break;
            case "老几啦":
                queryBossStatus(cq, event);
                break;
            case "预约":
                orderBoss(cq, event, msg);
                break;
            case "取消预约":
                cancelEarliestOneOrder(cq, event);
                break;
            case "取消所有预约":
                cancelAllOrder(cq, event);
                break;
            case "预约信息":
                queryOrderList(cq, event, rawmsg);
                break;
            case "出刀":
                battle(cq, event);
                break;
            case "挂树":
                hangingTree(cq, event);
                break;
            case "收刀":
                battened(cq, event, msg);
                break;
            case "代刀":
                helpBattle(cq, event, rawmsg);
                break;
            case "调整boss":
                adjustBossStatus(cq, event, msg);
                break;
            case "今日出刀情况":
                todayBattleInfo(cq, event, rawmsg);
                break;
            case "会战总况":
                allBattleInfo(cq, event, rawmsg);
                // 不满足上述命令时执行下一个插件
            default:
                return MESSAGE_IGNORE;
        }

        // 执行了上述命令之一后不再执行后面的插件了
        return MESSAGE_BLOCK;
    }

    private void showMenu(CoolQ cq, CQGroupMessageEvent event) {
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        String menu = "\n说明：命令后面带有*号表示需要管理员或者群主才能开启的命令，**表示只有群主能使用的命令，使用命令时不需要加*号" + "\n" +
                "#开启机器人*：开启后才能所有功能" + "\n" +
                "#关闭机器人*：关闭后除了菜单功能都不能再使用，但会保留关闭之前的所有数据" + "\n" +
                "#创建公会 公会名字*：创建后才能开始进行公会战，例如：“创建公会 樱花庄”" + "\n" +
                "#结束会战*：结束此次公会战并解散公会，但是会保留此次会战的记录，直到下一次创建公会" + "\n" +
                "#入会：成员加入公会，这个命令是后面所有命令的前提条件" + "\n" +
                "#开启会战*：开启后机器人会初始化boss信息" + "\n" +
                "#退会：成员退出此次会战，退会不会清除之前的出刀记录" + "\n" +
                "#修改公会 新的公会名字*：修改已经创建好了的公会的名字" + "\n" +
                "#踢出公会 @要踢的人**：群主可用这个命令将指定成员踢出公会" + "\n" +
                "#老几啦：查看当前公会战boss状态信息以及成员信息" + "\n" +
                "#预约 boss数字 预估伤害：比如“预约 1 50”,预约后请尽量按照预约的顺序进行出刀，避免记录出错" + "\n" +
                "#取消预约：取消最早的一条预约记录" + "\n" +
                "#取消所有预约：取消自己所有的预约记录" + "\n" +
                "#预约信息 @要查看信息的人：查看@的人的所有预约情况，不@表示查看所有成员的所有预约情况" + "\n" +
                "#出刀：表示自己正在出刀中" + "\n" +
                "#挂树：表示挂树了，等待大家来救" + "\n" +
                "#收刀 伤害：表示自己出完当前刀了，举例：收刀 50，该命令可以不进行预约和出刀直接用" + "\n" +
                "#代刀 @被代刀的人 伤害*：帮人出刀,该命令可以不进行预约和出刀直接用" + "\n" +
                "#调整boss 第几周目 老几 当前血量：例如，“调整boss 2 5 200”，表示将第二周目老五血量调整为200w，同时会将该boss设定为当前公会正在攻打的boss" + "\n" +
                "#今日出刀情况 @要查看信息的人：查看@的人的今日出刀记录，不@表示查看所有成员的今日出刀记录，如果要查看已经退出了公会的成员的数据请加上@参数" + "\n" +
                "#会战总况 @要查看信息的人：查看@的人的此次会战公会战情况，不@表示查看所有成员的此次会战情况，如果要查看已经退出了公会的成员的数据请加上@参数" + "\n";
        cq.sendGroupMsg(groupID, CQCode.at(userID) + menu, false);
    }


    private void updateUnion(CoolQ cq, CQGroupMessageEvent event, String[] msg) {
        long userID = event.getSender().getUserId();
        long groupID = event.getGroupId();
        String[] parameters = StringUtils.trimStrArr(msg);
        String permission = event.getSender().getRole();

        if ("member".equals(permission)) {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
        } else {
            if (parameters.length != 2) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
            } else if (parameters.length == 2) {
                Unions unions = unionsService.findUnionByGroupID(groupID);
                if (null == unions) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "公会还没建立，请先创建公会！", false);
                } else {
                    unionsService.updateUnionName(groupID, parameters[1]);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "公会名已修改为\"" + parameters[1] + "\"", false);
                }
            }
        }
    }

    public void kickOutUnion(CoolQ cq, CQGroupMessageEvent event, String rawmsg) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        Unions unions = unionsService.findUnionByGroupID(groupID);
        String permission = event.getSender().getRole();

        if ("owner".equals(permission)) {
            if (unionsService.findUnionByGroupID(groupID) != null) {
                String[] parameters = StringUtils.trimStrArr(rawmsg.split(" "));
                if (parameters.length != 2) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                } else if (parameters.length == 2) {
                    // 解析出被踢人的QQ号是多少
                    Long ateduserID = StringUtils.parseCQGetQQNumber(parameters[1]);
                    // 如果是踢自己
                    if (ateduserID == userID) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "不能踢出本人，请使用退会命令！", false);
                    } else {
                        // 判断要踢的人在不在公会里
                        if (null == memberService.findMemberByUserID(ateduserID, groupID)) {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "成员" + CQCode.at(ateduserID) + "还没加入公会呢！", false);
                        } else {
                            memberService.deleteMemberByUserID(ateduserID, groupID);
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "成功将" + CQCode.at(ateduserID) + "踢出\"" + unions.getUnionName() + "\"公会！", false);
                        }
                    }
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
        }

    }

    public void queryBossStatus(CoolQ cq, CQGroupMessageEvent event) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        int index;


        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 获取当前正在打的boss
                BossList bossList = bossListService.findBossListByGroupID(groupID);
                int whichOne = bossList.getWhichOne();
                int rounds = bossList.getRounds();
                // 获取预约当前boss的成员列表
                List<OrderList> orderLists = orderListService.findCurrentOrderList(groupID, whichOne, rounds);
                // 获取当前boss挂树中的成员列表
                List<TreeList> treeLists = treeListService.findCurrentTreeList(groupID, whichOne, rounds, BotConstants.ON_TREE);
                // 获取正在打当前boss的成员列表
                List<BattlingList> battlingLists = battlingListService.findBattlingListByGroupID(groupID);
                // 获取当前boss的出刀记录(要收刀或者代刀命令之后的才算)
                List<BattleLog> battleLogs = battleLogService.findCurrentBossBattleLog(groupID, whichOne, rounds);
                // 拼接字符串
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("当前为第").append(rounds).append("周目。").append("\n")
                        .append("当前为老").append(whichOne).append("，boss血量剩余：");
                switch (whichOne) {
                    case 1:
                        msgBuilder.append(bossList.getBoss1RemainHP());
                        break;
                    case 2:
                        msgBuilder.append(bossList.getBoss2RemainHP());
                        break;
                    case 3:
                        msgBuilder.append(bossList.getBoss3RemainHP());
                        break;
                    case 4:
                        msgBuilder.append(bossList.getBoss4RemainHP());
                        break;
                    case 5:
                        msgBuilder.append(bossList.getBoss5RemainHP());
                        break;
                }
                msgBuilder.append("w。").append("\n");
                msgBuilder.append("当前预约老").append(whichOne).append("的记录一共有：").append(orderLists.size()).append("条，分别是：").append("\n");
                index = 1;
                for (OrderList orderList : orderLists) {
                    msgBuilder.append(index++).append("、").append(CQCode.at(orderList.getMemberID())).append("，预估伤害：").append(orderList.getDamage()).append("w\n");
                }
                msgBuilder.append("当前正在出刀老").append(whichOne).append("的人一共有：").append(battlingLists.size()).append("位，分别是：").append("\n");
                for (BattlingList battlingList : battlingLists) {
                    msgBuilder.append(CQCode.at(battlingList.getMemberID()));
                }
                if (battlingLists.size() > 0) {
                    msgBuilder.append("\n");
                }
                msgBuilder.append("当前挂树").append("的人一共有：").append(treeLists.size()).append("位，分别是：").append("\n");
                for (TreeList treeList : treeLists) {
                    msgBuilder.append(CQCode.at(treeList.getMemberID()));
                }
                if (treeLists.size() > 0) {
                    msgBuilder.append("\n");
                }
                msgBuilder.append("当前老").append(whichOne).append("已出刀数共有：").append(battleLogs.size()).append("刀，分别是：").append("\n");
                index = 1;
                for (BattleLog battleLog : battleLogs) {
                    msgBuilder.append(index++).append("、").append(CQCode.at(battleLog.getMemberID())).append("伤害：").append(battleLog.getDamage()).append("w\n");
                }
                if (battleLogs.size() > 0) {
                    msgBuilder.append("\n");
                }

                cq.sendGroupMsg(groupID, msgBuilder.toString(), false);
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }
    }

    public void orderBoss(CoolQ cq, CQGroupMessageEvent event, String[] msg) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        String[] parameters = StringUtils.trimStrArr(msg);

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 获取公会当前正在打的boss
                BossList bossList = bossListService.findBossListByGroupID(groupID);
                int whichOne = bossList.getWhichOne();
                int rounds = bossList.getRounds();
                if (parameters.length != 3) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                } else if (parameters.length == 3) {
                    // 先判断预约的boss序号是否合理
                    if (Integer.parseInt(parameters[1]) < 1 || Integer.parseInt(parameters[1]) > 5) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "预约失败，boss只有五个，别乱来！", false);
                    } else {
                        // 如果预约的boss已经被杀了
                        if (Integer.parseInt(parameters[1]) < whichOne) {
                            // 说明是预约的下一周目的boss
                            orderListService.addOrder(userID, groupID, Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), rounds + 1);
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "预约成功，您的预约信息为：预约第" + (rounds + 1) + "周目的老" + Integer.parseInt(parameters[1])
                                    + "，预估伤害为" + Integer.parseInt(parameters[2]) + "w！", false);
                        } else {
                            // 预约的当前周目的boss
                            orderListService.addOrder(userID, groupID, Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), rounds);
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "预约成功，您的预约信息为：预约第" + rounds + "周目的老" + Integer.parseInt(parameters[1])
                                    + "，预估伤害为" + Integer.parseInt(parameters[2]) + "w！", false);
                        }
                    }
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }
    }

    private void cancelEarliestOneOrder(CoolQ cq, CQGroupMessageEvent event) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 先判断是否有预约记录
                if (0 == orderListService.findAllOrderListByUserID(userID, groupID).size()) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有预约记录！", false);
                } else {
                    // 清除最早的一条预约记录
                    orderListService.deleteEarliestOneByUserID(userID, groupID);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "取消一条预约记录成功！", false);
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }
    }

    private void cancelAllOrder(CoolQ cq, CQGroupMessageEvent event) {
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 先判断是否有预约记录
                if (0 == orderListService.findAllOrderListByUserID(userID, groupID).size()) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有预约记录！", false);
                } else {
                    // 清除所有的预约记录
                    orderListService.deleteAllByUserID(userID, groupID);
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "取消所有预约记录成功！", false);
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }

    private void queryOrderList(CoolQ cq, CQGroupMessageEvent event, String rawmsg) {
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        String[] parameters = StringUtils.trimStrArr(rawmsg.split(" "));
        StringBuilder msgBuilder = new StringBuilder();
        int index = 1;

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 查看指定公会所有人的预约信息
                if (parameters.length == 1) {
                    List<OrderList> orderLists = orderListService.findAllOrderListByGroup(groupID);
                    msgBuilder.append("当前公会所有预约记录有").append(orderLists.size()).append("条，分别是：").append("\n");
                    for (OrderList orderList : orderLists) {
                        msgBuilder.append(index++).append("、").append(CQCode.at(orderList.getMemberID())).append("预约了第").append(orderList.getRounds())
                                .append("周目的老").append(orderList.getWhichOne()).append("，预估伤害：").append(orderList.getDamage()).append("w\n");
                    }
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + msgBuilder.toString(), false);
                } else if (parameters.length == 2) {
                    // 解析出要查看的人的QQ号是多少
                    Long ateduserID = StringUtils.parseCQGetQQNumber(parameters[1]);
                    // 这里不需要判断这个人在不在公会，因为可能中途退会，但是数据还是保留着，应该还是要能查到他退会之前的数据的
                    // 判断是否有预约记录
                    List<OrderList> orderLists = orderListService.findAllOrderListByUserID(ateduserID, groupID);
                    if (0 == orderLists.size()) {
                        if (userID == ateduserID) {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,你没有预约记录！", false);
                        } else {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，" + CQCode.at(ateduserID) + "没有预约记录！", false);
                        }
                    } else {
                        if (userID == ateduserID) {
                            msgBuilder.append("你当前一共有").append(orderLists.size()).append("条预约记录，分别是：").append("\n");
                        } else {
                            msgBuilder.append(CQCode.at(ateduserID)).append("当前一共有").append(orderLists.size()).append("条预约记录，分别是：").append("\n");
                        }
                        // 显示指定成员所有的预约记录
                        for (OrderList orderList : orderLists) {
                            msgBuilder.append(index++).append("、").append("预约第").append(orderList.getRounds()).append("周目的老")
                                    .append(orderList.getWhichOne()).append("，预估伤害为：").append(orderList.getDamage()).append("w\n");
                        }
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + msgBuilder.toString(), false);
                    }
                } else {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }


    public void battle(CoolQ cq, CQGroupMessageEvent event) {

        long userID = event.getSender().getUserId();
        long groupID = event.getGroupId();

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 判断当前成员今日的出刀次数是否达到上限
                if (BotConstants.MAX_BATTLE_DAY == battleLogService.countDayLogByUserID(userID, groupID)) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "你今天已经出满三刀了，赶紧歇歇去吧！明日再来！", false);
                } else {
                    // 查询公会当前战斗的boss
                    BossList bossList = bossListService.findBossListByGroupID(groupID);
                    int whichOne = bossList.getWhichOne();
                    int rounds = bossList.getRounds();
                    // 先判断是否挂树中
                    TreeList treeList = treeListService.findCurrentTreeListByUserID(userID, groupID, BotConstants.ON_TREE);
                    if (null != treeList) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "你正在挂树中，先收刀确定下树再进行出刀！", false);
                    } else {
                        // 判断该成员是否预约了当前boss
                        if (0 == orderListService.findCurrentOrderListByUserID(userID, groupID, whichOne, rounds).size()) {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "请先预约该boss或者直接使用收刀命令！", false);
                        } else {
                            BattlingList battlingList = battlingListService.findBattlingListByUserID(userID, groupID);
                            if (null != battlingList) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "你正在出刀中，请勿重复出刀！", false);
                            } else {
                                // 出刀成功就清除最早的一条预约信息
                                orderListService.deleteEarliestOneByUserID(userID, groupID);
                                battlingListService.addBattling(userID, groupID, whichOne, rounds);
                                cq.sendGroupMsg(groupID,
                                        CQCode.at(userID) + "出刀成功，正在出刀" + "第" + rounds + "周目的老" + whichOne + "，请记得回来收刀哦！",
                                        false);
                            }
                        }
                    }
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }

    public void hangingTree(CoolQ cq, CQGroupMessageEvent event) {

        long userID = event.getSender().getUserId();
        long groupID = event.getGroupId();

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 判断当前成员今日的出刀次数是否达到上限
                if (BotConstants.MAX_BATTLE_DAY == battleLogService.countDayLogByUserID(userID, groupID)) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "你今天已经出满三刀了，赶紧歇歇去吧！明日再来！", false);
                } else {
                    // 先判断是否已经进行了出刀
                    if (null == battlingListService.findBattlingListByUserID(userID, groupID)) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "你都还没有出刀呢，挂什么挂？想让会长吐血吗？", false);
                    } else {
                        // 获取当前正在打的boss
                        BossList bossList = bossListService.findBossListByGroupID(groupID);
                        int whichOne = bossList.getWhichOne();
                        int rounds = bossList.getRounds();
                        // 判断是否正在挂树中
                        TreeList treeList = treeListService.findCurrentTreeListByUserID(userID, groupID, BotConstants.ON_TREE);
                        if (null != treeList) {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "你已经挂树了，还想挂高点吗？会长血压急速上升中！先收刀确定下树再进行出刀！", false);
                        } else {
                            treeListService.addHangingTree(userID, groupID, whichOne, rounds);
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "恭喜挂树成功，会长正在提刀来的路上，请注意躲避！(及时喊群友们救人下树哦)", false);
                        }
                    }
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }

    public void battened(CoolQ cq, CQGroupMessageEvent event, String[] msg) {
        long userID = event.getSender().getUserId();
        long groupID = event.getGroupId();

        String[] parameters = StringUtils.trimStrArr(msg);

        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 先判断命令是否合法
                if (parameters.length != 2) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                } else if (parameters.length == 2) {
                    int damage = Integer.parseInt(parameters[1]);

                    // 获取当前正在打的boss
                    BossList bossList = bossListService.findBossListByGroupID(groupID);
                    int whichOne = bossList.getWhichOne();
                    int rounds = bossList.getRounds();

                    switch (whichOne) {
                        case 1:
                            if (damage > bossList.getBoss1RemainHP()) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss1RemainHP() + "了,请确定你的伤害正常!", false);
                                return;
                            }
                            break;
                        case 2:
                            if (damage > bossList.getBoss2RemainHP()) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss2RemainHP() + "了,请确定你的伤害正常!", false);
                                return;
                            }
                            break;
                        case 3:
                            if (damage > bossList.getBoss3RemainHP()) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss3RemainHP() + "了,请确定你的伤害正常!", false);
                                return;
                            }
                            break;
                        case 4:
                            if (damage > bossList.getBoss4RemainHP()) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss4RemainHP() + "了,请确定你的伤害正常!", false);
                                return;
                            }
                            break;
                        case 5:
                            if (damage > bossList.getBoss5RemainHP()) {
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss5RemainHP() + "了,请确定你的伤害正常!", false);
                                return;
                            }
                            break;
                    }

                    // 判断当前成员今日的出刀次数是否达到上限
                    if (BotConstants.MAX_BATTLE_DAY == battleLogService.countDayLogByUserID(userID, groupID)) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "你今天已经出满三刀了，赶紧歇歇去吧！明日再来！", false);
                    } else {
                        // 判断是否使用过预约和出刀命令
                        // 如果使用了预约、出刀命令并且正在出刀中/挂树中
                        if (orderListService.findAllOrderListByUserID(userID, groupID) != null) {
                            orderListService.deleteEarliestOneByUserID(userID, groupID);
                        }
                        if (null != battlingListService.findBattlingListByUserID(userID, groupID)) {
                            // 先删除正在出刀中记录
                            battlingListService.deleteBattling(userID, groupID);
                        }
                        if (null != treeListService.findCurrentTreeListByUserID(userID, groupID, BotConstants.ON_TREE)) {
                            // 先修改当前挂树记录为下树了
                            int status = BotConstants.DOWN_TREE;
                            treeListService.updateHangingToDown(status, userID, groupID, whichOne, rounds);
                        }
                        // 然后统计收刀信息
                        battleLogService.addBattleLog(userID, groupID, damage, new Date(), whichOne, rounds);
                        // 更新当前boss状态
                        int bossRemainHP;
                        int nextOne;
                        int nextRounds;
                        switch (whichOne) {
                            case 1:
                                bossRemainHP = bossList.getBoss1RemainHP();
                                if ((bossRemainHP - damage) <= 0) {
                                    bossRemainHP = 0;
                                    nextOne = 2;
                                } else {
                                    bossRemainHP -= damage;
                                    nextOne = 1;
                                }
                                bossListService.updateBoss1Status(groupID, bossRemainHP, nextOne);
                                break;
                            case 2:
                                bossRemainHP = bossList.getBoss1RemainHP();
                                if ((bossRemainHP - damage) <= 0) {
                                    bossRemainHP = 0;
                                    nextOne = 3;
                                } else {
                                    bossRemainHP -= damage;
                                    nextOne = 2;
                                }
                                bossListService.updateBoss2Status(groupID, bossRemainHP, nextOne);
                                break;
                            case 3:
                                bossRemainHP = bossList.getBoss1RemainHP();
                                if ((bossRemainHP - damage) <= 0) {
                                    bossRemainHP = 0;
                                    nextOne = 4;
                                } else {
                                    bossRemainHP -= damage;
                                    nextOne = 3;
                                }
                                bossListService.updateBoss3Status(groupID, bossRemainHP, nextOne);
                                break;
                            case 4:
                                bossRemainHP = bossList.getBoss1RemainHP();
                                if ((bossRemainHP - damage) <= 0) {
                                    bossRemainHP = 0;
                                    nextOne = 5;
                                } else {
                                    bossRemainHP -= damage;
                                    nextOne = 4;
                                }
                                bossListService.updateBoss4Status(groupID, bossRemainHP, nextOne);
                                break;
                            case 5:
                                bossRemainHP = bossList.getBoss5RemainHP();
                                if ((bossRemainHP - damage) <= 0) {
                                    bossRemainHP = 0;
                                    nextOne = 1;
                                    nextRounds = rounds + 1;
                                    bossListService.resetAllBossRemainHP(
                                            BotConstants.BOSS1_HP,
                                            BotConstants.BOSS2_HP,
                                            BotConstants.BOSS3_HP,
                                            BotConstants.BOSS4_HP,
                                            BotConstants.BOSS5_HP,
                                            groupID);
                                } else {
                                    bossRemainHP -= damage;
                                    nextOne = 5;
                                    nextRounds = rounds;
                                }
                                bossListService.updateBoss5StatusWithRounds(groupID, bossRemainHP, nextOne, nextRounds);
                                break;
                        }
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "收刀成功！此次伤害为：" + damage + "w", false);
                    }
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }

    public void helpBattle(CoolQ cq, CQGroupMessageEvent event, String rawmsg) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        String permission = event.getSender().getRole();


        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                if ("admin".equals(permission) || "owner".equals(permission)) {
                    String[] parameters = StringUtils.trimStrArr(rawmsg.split(" "));
                    if (parameters.length != 3) {
                        cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                    } else if (parameters.length == 3) {
                        // 解析出被代刀人的QQ号是多少
                        Long ateduserID = StringUtils.parseCQGetQQNumber(parameters[1]);
                        // 判断该成员是否在公会中
                        if (null == memberService.findMemberByUserID(ateduserID, groupID)) {
                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "成员" + CQCode.at(ateduserID) + "还没加入公会呢！", false);
                        } else {
                            // 判断当前成员今日的出刀次数是否达到上限
                            if (BotConstants.MAX_BATTLE_DAY == battleLogService.countDayLogByUserID(ateduserID, groupID)) {
                                cq.sendGroupMsg(groupID, "成员" + CQCode.at(ateduserID) + "今天已经出满三刀了！明日再来！", false);
                            } else {
                                int damage = Integer.parseInt(parameters[2]);
                                // 获取当前正在打的boss
                                BossList bossList = bossListService.findBossListByGroupID(groupID);
                                int whichOne = bossList.getWhichOne();
                                int rounds = bossList.getRounds();

                                switch (whichOne) {
                                    case 1:
                                        if (damage > bossList.getBoss1RemainHP()) {
                                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss1RemainHP() + "了,请确定你的伤害正常!", false);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        if (damage > bossList.getBoss2RemainHP()) {
                                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss2RemainHP() + "了,请确定你的伤害正常!", false);
                                            return;
                                        }
                                        break;
                                    case 3:
                                        if (damage > bossList.getBoss3RemainHP()) {
                                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss3RemainHP() + "了,请确定你的伤害正常!", false);
                                            return;
                                        }
                                        break;
                                    case 4:
                                        if (damage > bossList.getBoss4RemainHP()) {
                                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss4RemainHP() + "了,请确定你的伤害正常!", false);
                                            return;
                                        }
                                        break;
                                    case 5:
                                        if (damage > bossList.getBoss5RemainHP()) {
                                            cq.sendGroupMsg(groupID, CQCode.at(userID) + "当前boss只剩" + bossList.getBoss5RemainHP() + "了,请确定你的伤害正常!", false);
                                            return;
                                        }
                                        break;
                                }
                                // 判断是否使用过预约和出刀命令
                                // 如果使用了预约、出刀命令并且正在出刀中/挂树中
                                if (orderListService.findAllOrderListByUserID(userID, groupID) != null) {
                                    orderListService.deleteEarliestOneByUserID(userID, groupID);
                                }
                                if (null != battlingListService.findBattlingListByUserID(ateduserID, groupID)) {
                                    // 先删除正在出刀中记录
                                    battlingListService.deleteBattling(ateduserID, groupID);
                                }
                                if (null != treeListService.findCurrentTreeListByUserID(ateduserID, groupID, BotConstants.ON_TREE)) {
                                    // 先修改当前挂树记录为下树了
                                    int status = BotConstants.DOWN_TREE;
                                    treeListService.updateHangingToDown(status, ateduserID, groupID, whichOne, rounds);
                                }
                                // 然后统计收刀信息
                                battleLogService.addBattleLog(ateduserID, groupID, damage, new Date(), whichOne, rounds);
                                // 更新当前boss状态
                                int bossRemainHP;
                                int nextOne;
                                int nextRounds;
                                switch (whichOne) {
                                    case 1:
                                        bossRemainHP = bossList.getBoss1RemainHP();
                                        if ((bossRemainHP - damage) <= 0) {
                                            bossRemainHP = 0;
                                            nextOne = 2;
                                        } else {
                                            bossRemainHP -= damage;
                                            nextOne = 1;
                                        }
                                        bossListService.updateBoss1Status(groupID, bossRemainHP, nextOne);
                                        break;
                                    case 2:
                                        bossRemainHP = bossList.getBoss1RemainHP();
                                        if ((bossRemainHP - damage) <= 0) {
                                            bossRemainHP = 0;
                                            nextOne = 3;
                                        } else {
                                            bossRemainHP -= damage;
                                            nextOne = 2;
                                        }
                                        bossListService.updateBoss2Status(groupID, bossRemainHP, nextOne);
                                        break;
                                    case 3:
                                        bossRemainHP = bossList.getBoss1RemainHP();
                                        if ((bossRemainHP - damage) <= 0) {
                                            bossRemainHP = 0;
                                            nextOne = 4;
                                        } else {
                                            bossRemainHP -= damage;
                                            nextOne = 3;
                                        }
                                        bossListService.updateBoss3Status(groupID, bossRemainHP, nextOne);
                                        break;
                                    case 4:
                                        bossRemainHP = bossList.getBoss1RemainHP();
                                        if ((bossRemainHP - damage) <= 0) {
                                            bossRemainHP = 0;
                                            nextOne = 5;
                                        } else {
                                            bossRemainHP -= damage;
                                            nextOne = 4;
                                        }
                                        bossListService.updateBoss4Status(groupID, bossRemainHP, nextOne);
                                        break;
                                    case 5:
                                        bossRemainHP = bossList.getBoss5RemainHP();
                                        if ((bossRemainHP - damage) <= 0) {
                                            bossRemainHP = 0;
                                            nextOne = 1;
                                            nextRounds = rounds + 1;
                                            bossListService.resetAllBossRemainHP(
                                                    BotConstants.BOSS1_HP,
                                                    BotConstants.BOSS2_HP,
                                                    BotConstants.BOSS3_HP,
                                                    BotConstants.BOSS4_HP,
                                                    BotConstants.BOSS5_HP,
                                                    groupID);
                                        } else {
                                            bossRemainHP -= damage;
                                            nextOne = 5;
                                            nextRounds = rounds;
                                        }
                                        bossListService.updateBoss5StatusWithRounds(groupID, bossRemainHP, nextOne, nextRounds);
                                        break;
                                }
                                cq.sendGroupMsg(groupID, CQCode.at(userID) + "代刀成功！" + CQCode.at(ateduserID) + "此次伤害为：" + damage + "w", false);
                            }
                        }
                    }
                } else {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，你没有权限使用此命令！", false);
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }


    }

    public void adjustBossStatus(CoolQ cq, CQGroupMessageEvent event, String[] msg) {

        long userID = event.getSender().getUserId();
        long groupID = event.getGroupId();
        String[] parameters = StringUtils.trimStrArr(msg);


        if (unionsService.findUnionByGroupID(groupID) != null) {
            if (bossListService.findBossListByGroupID(groupID) != null) {
                // 先判断命令是否合法
                if (parameters.length != 4) {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
                } else if (parameters.length == 4) {
                    int curRounds = Integer.parseInt(parameters[1]);
                    int curWhichOne = Integer.parseInt(parameters[2]);
                    int curHP = Integer.parseInt(parameters[3]);

                    // 获取正在打的哪一只
                    BossList bossList = bossListService.findBossListByGroupID(groupID);
                    int whichOne = bossList.getWhichOne();
                    int rounds = bossList.getRounds();

                    // 判断更新的是哪一只boss
                    switch (curWhichOne) {
                        case 1:
                            if (curHP == 0) {
                                curWhichOne++;
                            }
                            bossListService.updateBoss1StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
                            break;
                        case 2:
                            if (curHP == 0) {
                                curWhichOne++;
                            }
                            bossListService.updateBoss2StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
                            break;
                        case 3:
                            if (curHP == 0) {
                                curWhichOne++;
                            }
                            bossListService.updateBoss3StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
                            break;
                        case 4:
                            if (curHP == 0) {
                                curWhichOne++;
                            }
                            bossListService.updateBoss4StatusWithRounds(groupID, curHP, curRounds, curWhichOne);
                            break;
                        case 5:
                            int nextRounds = curRounds;
                            if (0 == curHP) {
                                curWhichOne = 1;
                                nextRounds++;
                                bossListService.resetAllBossRemainHP(
                                        BotConstants.BOSS1_HP,
                                        BotConstants.BOSS2_HP,
                                        BotConstants.BOSS3_HP,
                                        BotConstants.BOSS4_HP,
                                        BotConstants.BOSS5_HP,
                                        groupID);
                            }
                            bossListService.updateBoss5StatusWithRounds(groupID, curHP, curWhichOne, nextRounds);
                            break;
                    }
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "更新成功，请使用#老几啦查看当前boss状态", false);
                }
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,管理还没有开启会战,请联系管理开启会战!", false);
            }
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起,本群目前没有公会或者公会已被解散!", false);
        }

    }

    public void todayBattleInfo(CoolQ cq, CQGroupMessageEvent event, String rawmsg) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        StringBuilder msgBuilder = new StringBuilder();
        int index, index1;
        int battlendCount = 0;
        int unbattlendCount = 0;
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String[] parameters = StringUtils.trimStrArr(rawmsg.split(" "));
        // 查看所有还在公会的今日出刀记录，包含已经退会了的
        if (parameters.length == 1) {
            Map<Member, List<BattleLog>> everyBodyTodayBattlelog = new HashMap<>();
            List<Member> members = memberService.findMemberByGroupID(groupID);
            for (Member member : members) {
                List<BattleLog> battleLogs = battleLogService.findTodayBattleLogByUserID(member.getMemberID(), groupID);
                if (battleLogs.size() > 0) {
                    battlendCount++;
                } else {
                    unbattlendCount++;
                }
                everyBodyTodayBattlelog.put(member, battleLogs);
            }
            msgBuilder.append("今日到目前为止共出刀数为：").append(battlendCount + unbattlendCount)
                    .append("刀").append("\n").append("已出刀人数为：").append(battlendCount).append("人，还有")
                    .append(unbattlendCount).append("人一刀未出。").append("\n")
                    .append("详细出刀记录如下：").append("\n");

            for (Member member : everyBodyTodayBattlelog.keySet()) {
                index = index1 = 1;
                // 先判断是否有出刀记录
                if (everyBodyTodayBattlelog.get(member).size() > 0) {
                    msgBuilder.append(index++).append("、").append(CQCode.at(member.getMemberID())).append("今日已出刀数为：")
                            .append(everyBodyTodayBattlelog.get(member).size()).append("刀，分别为：").append("\n");
                    for (BattleLog battleLog : everyBodyTodayBattlelog.get(member)) {
                        msgBuilder.append("--").append("第").append(index1++).append("刀：于时间").append(sdf.format(battleLog.getBattleDate()))
                                .append("出刀第").append(battleLog.getRounds()).append("周目的老").append(battleLog.getWhichOne())
                                .append("，造成伤害：").append(battleLog.getDamage()).append("w\n");
                    }
                } else {
                    msgBuilder.append(index++).append("、").append("无出刀记录！").append("\n");
                }
            }
            cq.sendGroupMsg(groupID, CQCode.at(userID) + msgBuilder.toString(), false);
        } else if (parameters.length == 2) {
            // 解析出被查询成员今日出刀记录的QQ号是多少
            Long ateduserID = StringUtils.parseCQGetQQNumber(parameters[1]);
            if (userID == ateduserID) {
                msgBuilder.append("你");
            } else {
                msgBuilder.append("成员").append(CQCode.at(ateduserID));
            }
            // 这里也不需要判断成员是否在公会中，因为可能还有退会前的数据
            // 查询该成员的今日出刀记录
            List<BattleLog> battleLogs = battleLogService.findTodayBattleLogByUserID(ateduserID, groupID);
            index = 1;
            msgBuilder.append("今日一共出了").append(battleLogs.size())
                    .append("刀，详细记录如下：").append("\n");
            for (BattleLog battleLog : battleLogs) {
                msgBuilder.append("--").append("第").append(index++).append("刀：于时间").append(sdf.format(battleLog.getBattleDate()))
                        .append("出刀第").append(battleLog.getRounds()).append("周目的老").append(battleLog.getWhichOne())
                        .append("，造成伤害：").append(battleLog.getDamage()).append("w\n");
            }
            cq.sendGroupMsg(groupID, CQCode.at(userID) + msgBuilder.toString(), false);
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
        }
    }

    private void allBattleInfo(CoolQ cq, CQGroupMessageEvent event, String rawmsg) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        String[] parameters = StringUtils.trimStrArr(rawmsg.split(" "));
        StringBuilder msgBuilder = new StringBuilder();

        int index, index1;
        // 进行过预约的人数
        int orderPersonCount = 0;
        // 挂树过的人数
        int treePersonCount = 0;
        // 有出刀记录的人数
        int battlePersonCount = 0;

        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        // 查看所有人的所有会战记录
        if (parameters.length == 1) {
            Map<Member, List<BattleLog>> allMembersBattleLog = new HashMap<>();
            Map<Member, List<TreeList>> allMembersTreeList = new HashMap<>();

            List<Member> members = memberService.findMemberByGroupID(groupID);
            for (Member member : members) {
                // 所有成员的所有挂树记录
                List<TreeList> treeLists = treeListService.findAllTreeListByUserID(member.getMemberID(), groupID);
                treePersonCount += treeLists.size() > 1 ? 1 : 0;
                allMembersTreeList.put(member, treeLists);
                // 所有成员的所有出刀记录
                List<BattleLog> battleLogs = battleLogService.findAllBattleLogByUserID(member.getMemberID(), groupID);
                battlePersonCount += battleLogs.size() > 1 ? 1 : 0;
                allMembersBattleLog.put(member, battleLogs);
            }
            msgBuilder.append("此次会战共有").append(members.size()).append("人参与，其中未出刀人数为").append(members.size() - battlePersonCount)
                    .append("人，出刀人数为").append(battlePersonCount).append("人；").append("\n")
                    .append("没有挂过树的人有").append(members.size() - treePersonCount).append("人，挂过树的人有").append(treePersonCount).append("人；").append("\n")
                    .append("详细信息如下：").append("\n");
            index = 1;
            msgBuilder.append("所有挂树记录如下：").append("\n");
            for (Member member : members) {
                msgBuilder.append("==").append(index++).append("、").append(CQCode.at(member.getMemberID()))
                        .append("一共挂树").append(allMembersTreeList.get(member).size()).append("次，详细挂树记录(按照挂树顺序显示)如下：").append("\n");
                index1 = 1;
                for (TreeList treeList : allMembersTreeList.get(member)) {
                    msgBuilder.append("  --").append(index1++).append("、").append("在第").append(treeList.getRounds()).append("周目的老")
                            .append(treeList.getWhichOne()).append("挂树；").append("\n");
                }
            }
            index = 1;
            msgBuilder.append("所有出刀记录如下：").append("\n");
            for (Member member : members) {
                msgBuilder.append("==").append(index++).append("、").append(CQCode.at(member.getMemberID()))
                        .append("一共出刀").append(allMembersBattleLog.get(member).size()).append("次，详细出刀记录(按照出刀顺序显示)如下：").append("\n");
                index1 = 1;
                for (BattleLog battleLog : allMembersBattleLog.get(member)) {
                    msgBuilder.append("  --").append("第").append(index1++).append("刀：于时间").append(sdf.format(battleLog.getBattleDate()))
                            .append("出刀第").append(battleLog.getRounds()).append("周目的老").append(battleLog.getWhichOne())
                            .append("，造成伤害：").append(battleLog.getDamage()).append("w；").append("\n");
                }
            }
            cq.sendGroupMsg(groupID, CQCode.at(userID) + msgBuilder.toString(), false);
        } else if (parameters.length == 2) {
            // 解析出被查看此次会战所有数据的人的QQ号是多少
            Long ateduserID = StringUtils.parseCQGetQQNumber(parameters[1]);
            List<TreeList> treeLists = treeListService.findAllTreeListByUserID(ateduserID, groupID);
            List<BattleLog> battleLogs = battleLogService.findAllBattleLogByUserID(ateduserID, groupID);
            if (userID == ateduserID) {
                msgBuilder.append("你此次公会战的详细信息如下：").append("\n");
            } else {
                msgBuilder.append("成员").append(CQCode.at(ateduserID)).append("的此次公会战详细信息如下：").append("\n");
            }
            index = 1;
            msgBuilder.append("一共挂树了").append(treeLists.size()).append("次，详细挂树记录(按挂树顺序显示)如下：").append("\n");
            for (TreeList treeList : treeLists) {
                msgBuilder.append("  --").append(index++).append("、").append("在第").append(treeList.getRounds()).append("周目的老")
                        .append(treeList.getWhichOne()).append("挂树；").append("\n");
            }
            index = 1;
            msgBuilder.append("一共出了").append(battleLogs.size()).append("刀，详细出刀记录(按出刀顺序显示)如下：").append("\n");
            for (BattleLog battleLog : battleLogs) {
                msgBuilder.append("  --").append("第").append(index++).append("刀：于时间").append(sdf.format(battleLog.getBattleDate()))
                        .append("出刀第").append(battleLog.getRounds()).append("周目的老").append(battleLog.getWhichOne())
                        .append("，造成伤害：").append(battleLog.getDamage()).append("w；").append("\n");
            }
            cq.sendGroupMsg(groupID, msgBuilder.toString(), false);
        } else {
            cq.sendGroupMsg(groupID, CQCode.at(userID) + "命令格式错误！", false);
        }
    }
}
