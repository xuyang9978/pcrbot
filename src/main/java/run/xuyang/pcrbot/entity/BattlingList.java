package run.xuyang.pcrbot.entity;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

/**
 * 正在出刀成员列表实体类
 *
 * @author XuYang
 * @date 2020/6/4 17:11
 */
@Data
public class BattlingList {

    /**
     * id
     */
    private Integer id;

    /**
     * 正在出刀的成员QQ
     */
    private Long memberID;

    /**
     * 正在出刀的成员所属公会QQ群号
     */
    private Long groupID;

    /**
     * 正在打第几个boss
     */
    private Integer whichOne;

    /**
     * 正在打第几周目的boss
     */
    private Integer rounds;
}
