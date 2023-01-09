package com.lmq.web;

import com.lmq.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/7 16:15
 */
@Controller
public class ArchiveController {

    @Autowired
    IBlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
