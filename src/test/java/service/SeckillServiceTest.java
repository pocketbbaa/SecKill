package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.ExposerVO;
import vo.SeckillExecution;

/**
 * Created by admin on 2016/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;

    private final String slat = "jasd!@#)(&^%%#FGSDfsdaf@#^&%^*DSFGDE";

    @Test
    public void exportSeckillUrlTest(){
        long seckillId = 1L;

    }

    @Test
    public void executeSeckill(){
        long seckillId = 1L;
        ExposerVO exposerVO = seckillService.exportSeckillUrl(seckillId);
        System.out.println("是否暴露秒杀url：");
        System.out.println(exposerVO);
        if(exposerVO.isExposed()){
            long userPhone = 13880264646L;
            String md5 = exposerVO.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
            System.out.println("执行秒杀结果：");
            System.out.println(seckillExecution);
        }else{
            System.out.println(exposerVO);
        }

    }





}
