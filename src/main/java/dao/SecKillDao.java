package dao;

import model.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

}
//</editor-fold>
