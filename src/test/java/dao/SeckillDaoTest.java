package dao;

import model.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillDaoTest {

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void testreduceNumberById() throws Exception {
        long seckillId = 1L;
        SecKill secKill = secKillDao.queryById(seckillId);
        System.out.println("原库存：");
        System.out.println(secKill.getName() + "  " + secKill.getNumber());
        int count = secKillDao.reduceNumberById(seckillId, new Date());
        System.out.println("减库存结果=" + (count > 0));
        if(count > 0){
            SecKill secKill12 = secKillDao.queryById(seckillId);
            System.out.println("现库存：");
            System.out.println(secKill12.getName() + "  " + secKill12.getNumber());
        }
    }

    @Test
    public void testQueryById() throws Exception {
        SecKill secKill = secKillDao.queryById(1L);
        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<SecKill> list = secKillDao.queryAll();
        for (SecKill s : list) {
            System.out.println(s);
        }
    }

}