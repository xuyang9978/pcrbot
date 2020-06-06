package run.xuyang.pcrbot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 公会实体类
 *
 * @author XuYang
 * @date 2020/6/3 16:56
 */
@Data
public class Unions implements Serializable {

    /**
     * 公会id
     */
    private Integer id;

    /**
     * 公会名称
     */
    private String unionName;

    /**
     * 公会群号，解散公会时根据群号来判断
     */
    private Long groupID;

    /**
     * 一个公会可以有多个成员
     */
    private List<Member> members;

}
