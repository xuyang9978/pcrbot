package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.Member;

import java.util.List;

/**
 * 公会成员业务接口
 *
 * @author XuYang
 * @date 2020/6/3 20:28
 */
public interface MemberService {

    /**
     * 根据用户QQ号查询是否在公会成员列表中
     *
     * @param userID  用户QQ号
     * @param groupID 用户所在公会
     */
    Member findMemberByUserID(long userID, long groupID);

    /**
     * 添加一个QQ到公会成员中
     *
     * @param userID 用户QQ号
     */
    void addMember(long userID, long groupID);

    /**
     * 查询指定公会的所有成员
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的所有成员
     */
    List<Member> findMemberByGroupID(long groupID);

    /**
     * 删除指定公会的所有成员
     *
     * @param groupID 公会所在QQ群号
     */
    void deleteAllByGroupID(long groupID);

    /**
     * 删除公会中指定QQ号的成员
     *
     * @param userID  成员QQ号
     * @param groupID 成员所在公会
     */
    void deleteMemberByUserID(long userID, long groupID);

    /**
     * 统计指定公会当前在会人数
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的当前在会人数
     */
    int countByGroupID(long groupID);
}
