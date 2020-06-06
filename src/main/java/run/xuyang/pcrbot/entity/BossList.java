package run.xuyang.pcrbot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 公会战boss列表实体类
 *
 * @author XuYang
 * @date 2020/6/4 13:20
 */
@Data
public class BossList implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 当前周目数
     */
    private Integer rounds;

    /**
     * boss1的总血量
     */
    private Integer boss1HP;

    /**
     * boss2的总血量
     */
    private Integer boss2HP;

    /**
     * boss3的总血量
     */
    private Integer boss3HP;

    /**
     * boss4的总血量
     */
    private Integer boss4HP;

    /**
     * boss5的总血量
     */
    private Integer boss5HP;

    /**
     * 当前周目boss1的剩余血量
     */
    private Integer boss1RemainHP;

    /**
     * 当前周目boss2的剩余血量
     */
    private Integer boss2RemainHP;

    /**
     * 当前周目boss3的剩余血量
     */
    private Integer boss3RemainHP;

    /**
     * 当前周目boss4的剩余血量
     */
    private Integer boss4RemainHP;

    /**
     * 当前周目boss5的剩余血量
     */
    private Integer boss5RemainHP;

    /**
     * 属于哪一个公会QQ群的boss
     */
    private Long groupID;

    /**
     * 当前打到老几了
     */
    private Integer whichOne;
}
