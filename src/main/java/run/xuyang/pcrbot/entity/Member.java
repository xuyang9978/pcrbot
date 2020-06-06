package run.xuyang.pcrbot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 公会成员实体类
 *
 * @author XuYang
 * @date 2020/6/3 16:58
 */
@Data
public class Member implements Serializable {

    /**
     * 成员id
     */
    private Integer id;

    /**
     * 成员QQ号
     */
    private Long memberID;

    /**
     * 成员所在QQ群号，用于确定成员所在公会
     */
    private Long groupID;
}
