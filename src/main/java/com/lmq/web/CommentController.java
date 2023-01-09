package com.lmq.web;

import com.lmq.pojo.Comment;
import com.lmq.pojo.User;
import com.lmq.service.IBlogService;
import com.lmq.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/4 20:11
 */
@Controller
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IBlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
       // Comment comments = (Comment)model.getAttribute("comments");
       // System.out.println(comments);
        System.out.println("===========================================");
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session,Model model){
        Long blogId = comment.getBlog().getId();  //对应一个blog
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User)session.getAttribute("user");
        model.addAttribute("tempInfo",true);
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdmin(true);
            comment.setNickname("博主");
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
