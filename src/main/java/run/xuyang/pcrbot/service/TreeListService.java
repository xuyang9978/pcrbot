package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.TreeList;

import java.awt.image.PixelGrabber;
import java.util.List;

/**
 * 挂树列表业务层接口
 *
 * @author XuYang
 * @date 2020/6/4 13:41
 */
public interface TreeListService {

    /**
     * 查询指定公会所有挂树成员
     *
     * @param groupID 公会所在QQ群号
     * @return 该公会所欲挂树成员
     */
    List<TreeList> findAllTreeListByGroupID(long groupID);

    /**
     * 删除指定公会的所有挂树列表
     *
     * @param groupID 公会所在QQ群号
     */
    void deleteAllByGroupID(long groupID);

    /**
     * 查询指定公会当前的挂树列表
     *
     * @param groupID  公会所在QQ群号
     * @param whichOne 哪一个boss挂树
     * @param rounds   第几周目的boss挂树
     * @param onTree   挂树中
     * @return 该公会当前的挂树列表
     */
    List<TreeList> findCurrentTreeList(long groupID, int whichOne, int rounds, int onTree);

    /**
     * 查询指定成员在当前boss是否挂树
     *
     * @param userID  查询的成员QQ号
     * @param groupID 成员所在公会QQ群号
     * @param status  挂树状态
     * @return 如果正在挂树中则返回该对象，否则返回null
     */
    TreeList findCurrentTreeListByUserID(long userID, long groupID, int status);

    /**
     * 上树
     *
     * @param userID   挂树的成员QQ号
     * @param groupID  挂树的成员所在公会的QQ群号
     * @param whichOne 挂在第几个boss身上
     * @param rounds   这个boss是第几周目的
     */
    void addHangingTree(long userID, long groupID, int whichOne, int rounds);

    /**
     * 下树
     *
     * @param status   下树状态
     * @param userID   下树的成员QQ号
     * @param groupID  成员所在公会
     * @param whichOne 是哪一个boss
     * @param rounds   第几周目的
     */
    void updateHangingToDown(int status, long userID, long groupID, int whichOne, int rounds);

    /**
     * 查询指定成员的所有挂树记录
     *
     * @param userID  成员QQ号
     * @param groupID 成员所在QQ号
     * @return 该成员的所有挂树记录
     */
    List<TreeList> findAllTreeListByUserID(Long userID, long groupID);
}
