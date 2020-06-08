package run.xuyang.pcrbot.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;
import org.springframework.stereotype.Component;
import run.xuyang.pcrbot.constants.BotConstants;
import run.xuyang.pcrbot.entity.Member;
import run.xuyang.pcrbot.entity.Unions;
import run.xuyang.pcrbot.service.MemberService;
import run.xuyang.pcrbot.service.UnionsService;

/**
 * 加入公会
 *
 * @author XuYang
 * @date 2020/6/4 20:55
 */
@Component
public class JoinUnionPlugin extends CQPlugin {

    private final MemberService memberService;

    private final UnionsService unionsService;

    public JoinUnionPlugin(MemberService memberService, UnionsService unionsService) {
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

        if ("入会".equals(msg)) {
            if (null == unions) {
                cq.sendGroupMsg(groupID, CQCode.at(userID) + "对不起，管理员还没有创建公会，请联系管理员创建公会后再入会！", false);
            } else {
                if (null == member) {
                    // 如果公会人数已经达到上限了
                    if (BotConstants.MAX_MEMBER_SIZE == memberService.countByGroupID(groupID)) {
                        cq.sendGroupMsg(groupID,
                                CQCode.at(userID) + "对不起，公会人数已经达到" + BotConstants.MAX_MEMBER_SIZE + "人的限制了，所以暂时无法加入公会！",
                                false);
                    } else {
                        memberService.addMember(userID, groupID);
                        cq.sendGroupMsg(
                                groupID,
                                CQCode.at(userID) + "加入公会\"" + unions.getUnionName() + "\"成功！",
                                false);
                    }
                } else {
                    cq.sendGroupMsg(groupID, CQCode.at(userID) + "你已经在公会中了!", false);
                }
            }
        }
        // 判断是否入会来确定是否要继续往下执行后面的命令
        if (null == memberService.findMemberByUserID(userID, groupID)) {
            //cq.sendGroupMsg(groupID, CQCode.at(userID) + "要想使用会战相关命令,请先使用入会命令加入公会!", false);
            return MESSAGE_BLOCK;
        } else {
            return MESSAGE_IGNORE;
        }
    }
}
