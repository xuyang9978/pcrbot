package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.Unions;
import run.xuyang.pcrbot.mapper.UnionsMapper;
import run.xuyang.pcrbot.service.UnionsService;

/**
 * 机器人状态业务接口实现类
 *
 * @author XuYang
 * @date 2020/6/3 20:30
 */
@Service
public class UnionServiceImpl implements UnionsService {

    private final UnionsMapper unionsMapper;

    public UnionServiceImpl(UnionsMapper unionsMapper) {
        this.unionsMapper = unionsMapper;
    }

    @Override
    public Unions findUnionByGroupID(long groupID) {
        return unionsMapper.findUnionByGroupID(groupID);
    }

    @Override
    public void addUnion(String unionName, long groupID) {
        unionsMapper.addUnion(unionName, groupID);
    }

    @Override
    public void updateUnionName(long groupID, String newUnionName) {
        unionsMapper.updateUnionName(groupID, newUnionName);
    }

    @Override
    public void deleteUnion(long groupID) {
        unionsMapper.deleteUnion(groupID);
    }
}
