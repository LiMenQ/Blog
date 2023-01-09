package com.lmq.web.admin;

import com.lmq.pojo.User;
import com.lmq.service.IUserService;
import com.lmq.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/29 20:58
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private IUserService userService;

    @GetMapping  //跳转到登录
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//不要在页面拿到密码
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }



    //注销用户
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");  //清空session
        return "redirect:/admin";
    }







}
