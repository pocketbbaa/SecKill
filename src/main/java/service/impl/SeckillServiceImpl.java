package service.impl;

import dao.SecKillDao;
import dao.SuccessKillDao;
import dao.cache.RedisDao;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import model.SecKill;
import model.SuccessKilled;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import service.SeckillService;
import vo.ExposerVO;
import vo.SeckillExecution;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/6.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String slat = "jasd!@#)(&^%%#FGSDfsdaf@#^&%^*DSFGDE";

    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    @Autowired
    private RedisDao redisDao;


    @Override
    public List<SecKill> getSeckillList() {

        return secKillDao.queryAll();
    }

    @Override
    public SecKill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    @Override
    public ExposerVO exportSeckillUrl(long seckillId) {
        //redis缓存优化
        //1、在redis中查找对象
        SecKill secKill = redisDao.getSeckill(seckillId);
        if (secKill == null) {
            //2、查询数据库
            secKill = secKillDao.queryById(seckillId);
            if (secKill == null) {
                return new ExposerVO(false, seckillId);
            } else {
                //3、存入redis缓存
                redisDao.putSeckill(secKill);
            }
        }

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date now = new Date();
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new ExposerVO(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMD5(seckillId);
        return new ExposerVO(true, md5, seckillId);
    }

    //创建MD5密文
    private static String getMD5(long seckillId) {

        String slat = "jasd!@#)(&^%%#FGSDfsdaf@#^&%^*DSFGDE";
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static void main(String[] args){
        String md5 = getMD5(4L);
        System.out.println(md5);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀数据被篡改");
        }
        try {
            //执行秒杀逻辑：减库存+记录购买行为
            Date nowTime = new Date();
            //优化点：先执行insert操作再执行update操作
            //insert操作不会报错，有主键冲突返回0，不持有行级锁
            //记录购买行为
            int insertCount = successKillDao.add(seckillId, userPhone, new Date());
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("重复秒杀");
            } else {
                //减库存
                int updateCount = secKillDao.reduceNumberById(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录，秒杀结束
                    throw new SeckillCloseException("秒杀已经关闭");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKillDao.queryById(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error("秒杀执行异常");
            throw new SeckillException("秒杀结果错误" + e.getMessage());
        }
    }

    //使用java调用mysql的存储过程
    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀数据被篡改");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("seckillId", seckillId);
        paramMap.put("phone", userPhone);
        paramMap.put("md5", md5);
        paramMap.put("result", null);
        try {
            secKillDao.killByProecduce(paramMap);
            //获取result
            int result = MapUtils.getInteger(paramMap, "result", -2);
            if (result == 1) {
                SuccessKilled successKilled = successKillDao.queryById(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            } else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }
    }
}
