package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.TreeList;
import run.xuyang.pcrbot.mapper.TreeListMapper;
import run.xuyang.pcrbot.service.TreeListService;

import java.util.List;

/**
 * 挂树列表业务接口实现类
 *
 * @author XuYang
 * @date 2020/6/4 13:41
 */
@Service
public class TreeListServiceImpl implements TreeListService {

    private final TreeListMapper treeListMapper;

    public TreeListServiceImpl(TreeListMapper treeListMapper) {
        this.treeListMapper = treeListMapper;
    }

    @Override
    public List<TreeList> findAllTreeListByGroupID(long groupID) {
        return treeListMapper.findAllTreeListByGroupID(groupID);
    }

    @Override
    public void deleteAllByGroupID(long groupID) {
        treeListMapper.deleteAllByGroupID(groupID);
    }

    @Override
    public List<TreeList> findCurrentTreeList(long groupID, int whichOne, int rounds, int onTree) {
        return treeListMapper.findCurrentTreeList(groupID, whichOne, rounds, onTree);
    }

    @Override
    public TreeList findCurrentTreeListByUserID(long userID, long groupID, int status) {
        return treeListMapper.findCurrentTreeListByUserID(userID, groupID, status);
    }

    @Override
    public void addHangingTree(long userID, long groupID, int whichOne, int rounds) {
        treeListMapper.addHangingTree(userID, groupID, whichOne, rounds);
    }

    @Override
    public void updateHangingToDown(int status, long userID, long groupID, int whichOne, int rounds) {
        treeListMapper.updateHangingToDown(status, userID, groupID, whichOne, rounds);
    }

    @Override
    public List<TreeList> findAllTreeListByUserID(Long userID, long groupID) {
        return treeListMapper.findAllTreeListByUserID(userID, groupID);
    }
}
