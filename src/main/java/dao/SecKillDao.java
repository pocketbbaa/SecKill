package dao;

import model.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/6.
 */
public interface SecKillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumberById(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据ID查询
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 查询所有
     * @return
     */
    List<SecKill> queryAll();

    /**
     * 调用存储过程执行秒杀
     * @param paramMap
     */
    void killByProecduce(Map<String,Object> paramMap);

}
//</editor-fold>
