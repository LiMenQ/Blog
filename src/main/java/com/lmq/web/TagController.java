package com.lmq.web;

import com.lmq.pojo.Tag;
import com.lmq.service.IBlogService;
import com.lmq.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/7 14:24
 */
@Controller
public class TagController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IBlogService blogService;


    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(9999);
        if(id == -1){
            id = tags.get(0).getId();  //拿到第一个
        }

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);

        return "tags";
    }


}
