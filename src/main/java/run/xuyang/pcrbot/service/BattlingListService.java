package run.xuyang.pcrbot.service;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.BattlingList;

import java.util.List;

/**
 * 正在出刀成员列表业务层接口
 *
 * @author XuYang
 * @date 2020/6/4 17:14
 */
public interface BattlingListService {

    /**
     * 查找指定公会的所有正在出刀记录
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会所有正在出刀记录
     */
    List<BattlingList> findBattlingListByGroupID(long groupID);

    /**
     * 删除指定公会的所有正在出刀记录
     *
     * @param groupID 公会所在QQ群号
     */
    void deleteAllBattlingListByGroupID(long groupID);

    /**
     * 查询指定成员是否正在出刀
     *
     * @param userID  查询的成员QQ号
     * @param groupID 成员所在公会QQ群号
     * @return 该成员是否正在出刀
     */
    BattlingList findBattlingListByUserID(long userID, long groupID);

    /**
     * 添加一个成员正在出刀的记录
     *
     * @param userID   正在出刀成员的QQ号
     * @param groupID  正在出刀成员所在公会QQ群号
     * @param whichOne 正在打哪个boss
     * @param rounds   正在打的boss是第几周目的
     */
    void addBattling(long userID, long groupID, int whichOne, int rounds);

    /**
     * 删除成员正在出刀记录
     *
     * @param userID  要删除的成员QQ号
     * @param groupID 成员所在公会
     */
    void deleteBattling(long userID, long groupID);
}
