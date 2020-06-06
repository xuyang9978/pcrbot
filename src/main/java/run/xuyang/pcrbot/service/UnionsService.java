package run.xuyang.pcrbot.service;

import run.xuyang.pcrbot.entity.Unions;


/**
 * 公会业务层接口
 *
 * @author XuYang
 * @date 2020/6/3 20:29
 */
public interface UnionsService {

    /**
     * 根据群号查询该群的公会信息
     *
     * @param groupID 群号
     * @return 该群的工会信息
     */
    Unions findUnionByGroupID(long groupID);

    /**
     * 添加一个公会记录到公会表中
     *
     * @param unionName 公会名称
     * @param groupID   该公会的群号
     */
    void addUnion(String unionName, long groupID);

    /**
     * 更新本群的公会名
     *
     * @param groupID      群号
     * @param newUnionName 新的公会名
     */
    void updateUnionName(long groupID, String newUnionName);

    /**
     * 删除指定公会
     * @param groupID 公会所在QQ群号
     */
    void deleteUnion(long groupID);
}
