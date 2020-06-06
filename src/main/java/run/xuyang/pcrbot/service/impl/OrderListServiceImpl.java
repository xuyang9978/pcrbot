package run.xuyang.pcrbot.service.impl;

import org.springframework.stereotype.Service;
import run.xuyang.pcrbot.entity.OrderList;
import run.xuyang.pcrbot.mapper.OrderListMapper;
import run.xuyang.pcrbot.service.OrderListService;

import java.util.List;

/**
 * 预约出刀列表业务层接口实现类
 *
 * @author XuYang
 * @date 2020/6/4 13:37
 */
@Service
public class OrderListServiceImpl implements OrderListService {

    private final OrderListMapper orderListMapper;

    public OrderListServiceImpl(OrderListMapper orderListMapper) {
        this.orderListMapper = orderListMapper;
    }

    @Override
    public List<OrderList> findAllOrderListByGroup(long groupID) {
        return orderListMapper.findAllOrderListByGroup(groupID);
    }

    @Override
    public void deleteAllByGroupID(long groupID) {
        orderListMapper.deleteAllByGroupID(groupID);
    }

    @Override
    public List<OrderList> findCurrentOrderList(long groupID, int whichOne, int rounds) {
        return orderListMapper.findCurrentOrderList(groupID, whichOne, rounds);
    }

    @Override
    public void addOrder(long userID, long groupID, int whichOne, int damage, int rounds) {
        orderListMapper.addOrder(userID, groupID, whichOne, damage, rounds);
    }

    @Override
    public List<OrderList> findCurrentOrderListByUserID(long userID, long groupID, int whichOne, int rounds) {
        return orderListMapper.findCurrentOrderListByUserID(userID, groupID, whichOne, rounds);
    }

    @Override
    public void deleteEarliestOneByUserID(long userID, long groupID) {
        orderListMapper.deleteEarliestOneByUserID(userID, groupID);
    }

    @Override
    public List<OrderList> findAllOrderListByUserID(long userID, long groupID) {
        return orderListMapper.findAllOrderListByUserID(userID, groupID);
    }

    @Override
    public void deleteAllByUserID(long userID, long groupID) {
        orderListMapper.deleteAllByUserID(userID, groupID);
    }
}
