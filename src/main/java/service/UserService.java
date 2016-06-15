package service;

import model.User;

/**
 * Created by admin on 2016/6/15.
 */
public interface UserService {

    /**
     * 用户登录
     * @param user
     * @return
     */
    boolean userLogin(User user);

    /**
     * 判断电话存不存在
     * @param phone
     * @return
     */
    boolean phoneExist(Long phone);

}
