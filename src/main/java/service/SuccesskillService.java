package service;

import model.SuccessKilled;

import java.util.Date;

/**
 * Created by admin on 2016/6/7.
 */
public interface SuccesskillService {

    /**
     * 添加秒杀成功明细
     * @param seckillId
     * @param userPhone
     * @param createTime
     * @return
     */
    boolean addSuccessKill(long seckillId, long userPhone, Date createTime);


    /**
     * 根据秒杀ID和用户ID获取秒杀成功明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled getSuccesskillById(long seckillId,long userPhone);

}
