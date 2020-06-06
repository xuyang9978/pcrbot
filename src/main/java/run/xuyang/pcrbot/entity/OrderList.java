package run.xuyang.pcrbot.entity;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;

/**
 * 预约出刀列表实体类
 *
 * @author XuYang
 * @date 2020/6/4 13:32
 */
@Data
public class OrderList implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 预约出刀成员QQ号
     */
    private Long memberID;

    /**
     * 预约出刀成员所在公会QQ群号
     */
    private Long groupID;

    /**
     * 预约第几个boss
     */
    private Integer whichOne;

    /**
     * 预估伤害量
     */
    private Integer damage;

    /**
     * 系统计算预约的boss属于当前轮还是下一轮的
     */
    private Integer rounds;
}
