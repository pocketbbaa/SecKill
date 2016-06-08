package service.impl;

import dao.SuccessKillDao;
import model.SuccessKilled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SuccesskillService;

import java.util.Date;

/**
 * Created by admin on 2016/6/7.
 */
@Service
public class SuccessKillServiceImpl implements SuccesskillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SuccessKillDao successKillDao;


    @Override
    public boolean addSuccessKill(long seckillId, long userPhone, Date createTime) {

        createTime = new Date();
        int count = successKillDao.add(seckillId,userPhone,createTime);
        if(count >= 0){
            return true;
        }
        return false;
    }

    @Override
    public SuccessKilled getSuccesskillById(long seckillId, long userPhone) {
        return successKillDao.queryById(seckillId,userPhone);
    }
}
