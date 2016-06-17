package dao;

import dao.cache.RedisDao;
import model.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试redis缓存的连接使用
 * Created by admin on 2016/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class RedisDaoTest {

    private Long id = 4L;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SecKillDao secKillDao;


    @Test
    public void testSeckill(){
        SecKill secKill = redisDao.getSeckill(id);
        if(secKill == null){
            secKill = secKillDao.queryById(id);
            if(secKill != null){
                String result = redisDao.putSeckill(secKill);
                System.out.println(result);
                secKill = redisDao.getSeckill(id);
                System.out.println(secKill);
            }
        }

    }



}
