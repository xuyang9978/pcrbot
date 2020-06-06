package run.xuyang.pcrbot.entity;

import lombok.Data;

/**
 * 挂树列表实体类
 *
 * @author XuYang
 * @date 2020/6/4 13:38
 */
@Data
public class TreeList {

    /**
     * id
     */
    private Integer id;

    /**
     * 挂树成员QQ号
     */
    private Long memberID;

    /**
     * 挂树成员所在QQ群号
     */
    private Long groupID;

    /**
     * 挂树状态
     * 1表示正在挂树中，等待营救
     * 0表示已经下树了
     */
    private Integer status;

    /**
     * 第几个boss挂树
     */
    private Integer whichOne;

    /**
     * 第几周目的boss挂树
     */
    private Integer rounds;

}
