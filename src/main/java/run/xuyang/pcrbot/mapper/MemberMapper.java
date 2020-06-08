package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import run.xuyang.pcrbot.entity.Member;

import java.util.List;

/**
 * 成员持久层接口
 *
 * @author XuYang
 * @date 2020/6/3 18:00
 */
public interface MemberMapper {

    /**
     * 根据群号，也就是公会的唯一标识码来查找该公会的所有成员
     *
     * @param groupID 群号
     * @return 该群(公会)的所有成员
     */
    @Select(" select * from member where group_id=#{groupID} ")
    @Results(id = "memberMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "member_id", property = "memberID", javaType = Long.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class)
    })
    List<Member> findAllMembersByGroupId(long groupID);

    /**
     * 根据用户QQ号查询是否在公会成员列表中
     *
     * @param userID 用户QQ号
     */
    @Select(" select * from member where member_id=#{userID} and group_id=#{groupID} ")
    @ResultMap("memberMap")
    Member findMemberByUserID(long userID, long groupID);

    /**
     * 添加一个QQ到公会成员中
     *
     * @param userID 用户QQ号
     */
    @Insert(" insert into member(member_id, group_id) values(#{userID}, #{groupID}) ")
    void addMember(long userID, long groupID);

    /**
     * 查询指定公会的所有成员
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的所有成员
     */
    @Select(" select * from member where group_id=#{groupID} ")
    @ResultMap("memberMap")
    List<Member> findMemberByGroupID(long groupID);

    /**
     * 删除指定公会的所有成员
     *
     * @param groupID 公会所在QQ群号
     */
    @Delete(" delete from member where group_id=#{groupID} ")
    void deleteAllByGroupID(long groupID);

    /**
     * 删除公会中指定QQ号的成员
     *
     * @param userID 成员QQ号
     */
    @Delete(" delete from member where member_id=#{userID} and group_id=#{groupID} ")
    void deleteMemberByUserID(long userID, long groupID);

    /**
     * 统计指定公会当前在会人数
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会的当前在会人数
     */
    @Select(" select count(*) from member where group_id=#{groupID} ")
    int countByGroupID(long groupID);
}
