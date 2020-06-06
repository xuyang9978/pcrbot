package run.xuyang.pcrbot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出刀记录实体类
 *
 * @author XuYang
 * @date 2020/6/4 13:15
 */
@Data
public class BattleLog implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 出刀成员的QQ号
     */
    private Long memberID;

    /**
     * 出刀成员所在群
     */
    private Long groupID;

    /**
     * 这一刀的伤害
     */
    private Integer damage;

    /**
     * 这一刀出的时间，以收到收刀命令为准
     */
    private Date battleDate;

    /**
     * 这刀打的老几
     */
    private Integer whichOne;

    /**
     * 这刀属于第几周目
     */
    private Integer rounds;

}
