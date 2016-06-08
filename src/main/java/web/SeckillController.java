package web;

import model.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.SeckillService;

import java.util.List;

/**
 * Created by admin on 2016/6/7.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    //查询秒杀列表
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
        List<SecKill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }
    //根据ID查询秒杀信息
    @RequestMapping(value="/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){

        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        SecKill secKill = seckillService.getById(seckillId);
        if(secKill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",secKill);
        return "detail";
    }
}
