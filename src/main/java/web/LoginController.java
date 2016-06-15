package web;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;
import utils.RegexUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2016/6/15.
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/toLogin",method = RequestMethod.GET)
    public String toLogin(){

        return "login";
    }


    @RequestMapping(value="/login",method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("phone") String phone, ModelMap map,HttpSession session){

        //校验手机是否正确
        if(!RegexUtil.checkCellphone(phone)){
            map.put("error","请输入正确的手机号!");
            return new ModelAndView("/toLogin",map);
        }
        //判断用户有没有参与过秒杀
        if(userService.phoneExist(Long.parseLong(phone))){
            map.put("error","你已经参加过秒杀！");
            return new ModelAndView("/toLogin",map);
        }
        User user = new User(Long.parseLong(phone));
        if(!userService.userLogin(user)){
            map.put("error","登录失败");
            return new ModelAndView("/toLogin",map);
        }
        session.setAttribute("user",user);

        return new ModelAndView("redirect:/seckill/list");

    }

}
