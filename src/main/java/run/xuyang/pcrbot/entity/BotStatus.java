package run.xuyang.pcrbot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 机器人状态实体类
 *
 * @author XuYang
 * @date 2020/6/3 15:04
 */
@Data
public class BotStatus implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 机器人所在群号
     */
    private Long groupID;

    /**
     * 机器人是否开始
     */
    private Integer groupBotStatus;
}
