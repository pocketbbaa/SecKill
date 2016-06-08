package dao;

import model.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by admin on 2016/6/6.
 */
public interface SuccessKillDao {


    /**
     * 添加明细
     * @param
     * @return
     */
    int add(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone, @Param("createTime") Date createTime);


    /**
     * 查询明细
     * @param seckillId
     * @return
     */
    SuccessKilled queryById(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);


}
