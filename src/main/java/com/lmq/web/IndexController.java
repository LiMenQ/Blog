package com.lmq.web;

import com.lmq.NotFoundException;
import com.lmq.pojo.Blog;
import com.lmq.service.IBlogService;
import com.lmq.service.ICommentService;
import com.lmq.service.ITagService;
import com.lmq.service.ITypeService;
import com.lmq.sup.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/28 16:18
 */
@Controller
public class IndexController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private ICommentService commentService;



    @GetMapping("/")
    public String index(@PageableDefault(size = 7, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,Model model){

        model.addAttribute("page",blogService.listBlog(pageable));  //拿到分页数据
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 50, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,Model model,@RequestParam String query){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);

        return "search";
    }








    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        //Blog b = blogService.getBlogAndConvert(id);
        //System.out.println(b.getContent());
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        return "blog";
    }


}
