package service.impl;

import dao.SecKillDao;
import dao.SuccessKillDao;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import model.SecKill;
import model.SuccessKilled;
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
import java.util.List;

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
        SecKill secKill = secKillDao.queryById(seckillId);
        if (secKill == null) {
            return new ExposerVO(false, seckillId);
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
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("秒杀数据被篡改");
        }
        try {
            //执行秒杀逻辑：减库存+记录购买行为
            Date nowTime = new Date();
            //减库存
            int updateCount = secKillDao.reduceNumberById(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("秒杀已经关闭");
            } else {
                //记录购买行为
                int insertCount = successKillDao.add(seckillId, userPhone, new Date());
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("重复秒杀");
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
}
