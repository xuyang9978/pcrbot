package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.Member;
import run.xuyang.pcrbot.mapper.MemberMapper;
import run.xuyang.pcrbot.service.MemberService;

import java.util.List;

/**
 * 机器人状态业务接口实现类
 *
 * @author XuYang
 * @date 2020/6/3 20:29
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    @Override
    public Member findMemberByUserID(long userID, long groupID) {
        return memberMapper.findMemberByUserID(userID, groupID);
    }

    @Override
    public void addMember(long userID, long groupID) {
        memberMapper.addMember(userID, groupID);
    }

    @Override
    public List<Member> findMemberByGroupID(long groupID) {
        return memberMapper.findMemberByGroupID(groupID);
    }

    @Override
    public void deleteAllByGroupID(long groupID) {
        memberMapper.deleteAllByGroupID(groupID);
    }

    @Override
    public void deleteMemberByUserID(long userID, long groupID) {
        memberMapper.deleteMemberByUserID(userID, groupID);
    }

    @Override
    public int countByGroupID(long groupID) {
        return memberMapper.countByGroupID(groupID);
    }
}
