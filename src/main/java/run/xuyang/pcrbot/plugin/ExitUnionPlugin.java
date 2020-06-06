package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.entity.Member;
import run.xuyang.pcrbot.entity.Unions;
import run.xuyang.pcrbot.service.MemberService;
import run.xuyang.pcrbot.service.UnionsService;
import run.xuyang.pcrbot.util.StringUtils;

/**
 * 退出公会
 *
 * @author XuYang
 * @date 2020/6/4 21:02
 */
@Component
public class ExitUnionPlugin extends CQPlugin {

    private final MemberService memberService;

    private final UnionsService unionsService;

    public ExitUnionPlugin(MemberService memberService, UnionsService unionsService) {
        this.memberService = memberService;
        this.unionsService = unionsService;
    }

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {

        long groupID = event.getGroupId();
        long userID = event.getSender().getUserId();
        Member member = memberService.findMemberByUserID(userID, groupID);
        Unions unions = unionsService.findUnionByGroupID(groupID);
        String msg = event.getMessage();

        if ("退会".equals(msg)) {
            if (null == member) {
                cq.sendGroupMsg(
                        groupID,
                        CQCode.at(userID) + "对不起，你还没有加入本群公会\"" + unions.getUnionName() + "\"!",
                        false);
            } else {
                memberService.deleteMemberByUserID(userID, groupID);
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "退出公会\"" + unions.getUnionName() + "\"成功!", false);
            }
        }

        // 判断否还在公会中以确定是否还要执行后面的命令
        // 如果没有退会
        if (null != memberService.findMemberByUserID(userID, groupID)) {
            // 那就可以继续执行后面的命令
            return MESSAGE_IGNORE;
        } else {
            // 已经退出公会了那么久不再执行后面的命令
            return MESSAGE_BLOCK;
        }
    }
}
