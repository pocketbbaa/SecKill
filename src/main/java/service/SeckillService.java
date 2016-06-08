package service;

import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import model.SecKill;
import vo.ExposerVO;
import vo.SeckillExecution;

import java.util.List;

/**
 * Created by admin on 2016/6/6.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    SecKill getById(long seckillId);

    /**
     * 秒杀开启是否输出秒杀接口地址
     *
     * @param seckillId
     */
    ExposerVO exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;


}
