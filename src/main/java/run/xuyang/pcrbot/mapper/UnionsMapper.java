package run.xuyang.pcrbot.mapper;

import org.apache.ibatis.annotations.*;
import run.xuyang.pcrbot.entity.Unions;

import java.util.List;

/**
 * 公会持久层接口
 *
 * @author XuYang
 * @date 2020/6/3 17:04
 */
public interface UnionsMapper {

    /**
     * 根据群号查询该群的公会信息
     *
     * @param groupID 群号
     * @return 该群的工会信息
     */
    @Select(" select * from unions where group_id=#{groupID} ")
    @Results(id = "unionMap", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "union_name", property = "unionName", javaType = String.class),
            @Result(column = "group_id", property = "groupID", javaType = Long.class)
    })
    Unions findUnionByGroupID(long groupID);

    /**
     * 添加一个公会记录到公会表中
     *
     * @param unionName 公会名称
     * @param groupID 该公会的群号
     */
    @Insert(" insert into unions(union_name, group_id) values(#{unionName}, #{groupID}) ")
    void addUnion(String unionName, long groupID);

    /**
     * 更新本群的公会名
     *
     * @param groupID      群号
     * @param newUnionName 新的公会名
     */
    @Update(" update unions set union_name=#{newUnionName} where group_id=#{groupID} ")
    void updateUnionName(long groupID, String newUnionName);

    /**
     * 删除指定公会
     * @param groupID 公会所在QQ群号
     */
    @Delete(" delete from unions where group_id=#{groupID} ")
    void deleteUnion(long groupID);
}
