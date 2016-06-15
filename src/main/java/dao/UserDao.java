package dao;

import model.User;

/**
 * Created by admin on 2016/6/15.
 */
public interface UserDao {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 电话存不存在
     * @param phone
     * @return
     */
    int phoneExist(Long phone);

}
