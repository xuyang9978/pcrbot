package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.entity.Unions;
import run.xuyang.pcrbot.service.MemberService;
import run.xuyang.pcrbot.service.UnionsService;

/**
 * 菜单
 *
 * @author XuYang
 * @date 2020/6/8 16:20
 */
@Component
public class MenuPlugin extends CQPlugin {

    private final UnionsService unionsService;

    private final MemberService memberService;

    public MenuPlugin(UnionsService unionsService, MemberService memberService) {
        this.unionsService = unionsService;
        this.memberService = memberService;
    }


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage();
        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();

        if (msg.contains("菜单")) {
            String menu = "\n说明：命令后面带有*号表示需要管理员或者群主才能开启的命令，**表示只有群主能使用的命令，使用命令时不需要加*号" + "\n" +
                    "#开启机器人*：开启后才能所有功能" + "\n" +
                    "#关闭机器人*：关闭后除了菜单功能都不能再使用，但会保留关闭之前的所有数据" + "\n" +
                    "#创建公会 公会名字*：创建后才能开始进行公会战，例如：“创建公会 樱花庄”" + "\n" +
                    "#多少人了：查看当前有多少人加入公会了" + "\n" +
                    "#入会：成员加入公会，这个命令是后面所有命令的前提条件" + "\n" +
                    "#结束会战*：结束此次公会战并解散公会，但是会保留此次会战的记录，直到下一次创建公会" + "\n" +
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
        } else if (msg.contains("多少人了")) {
            Unions union = unionsService.findUnionByGroupID(groupID);
            if (union == null) {
                cq.sendGroupMsg(groupID,
                        CQCode.at(userID) + "该群公会还没有创建哟，暂时没有成员！",
                        false);
            } else {
                cq.sendGroupMsg(groupID, CQCode.at(userID) +
                        "公会\"" + union.getUnionName() + "\"目前成员数: " + memberService.countByGroupID(groupID) + " / 30",
                        false);
            }
        }
        return MESSAGE_IGNORE;
    }
}
