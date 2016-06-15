package service.impl;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * Created by admin on 2016/6/15.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public boolean userLogin(User user) {
        int count = userDao.addUser(user);
        return count > 0;
    }

    @Override
    public boolean phoneExist(Long phone) {
        int count = userDao.phoneExist(phone);
        return count > 0;
    }
}
