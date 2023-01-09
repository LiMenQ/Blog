package com.lmq.web.admin;

import com.lmq.pojo.Blog;
import com.lmq.pojo.User;
import com.lmq.service.IBlogService;
import com.lmq.service.ITagService;
import com.lmq.service.ITypeService;
import com.lmq.sup.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 12:10
 */
@Controller
@RequestMapping("/admin")
public class AdminBlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;

    @GetMapping("/blogs")   //返回的是博客管理页面
    public String blogs(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }


    @PostMapping("/blogs/search")   //返回的是博客管理页面
    public String search(@PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";  //返回片段
    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String eidtInput(@PathVariable Long id , Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());

        Blog blog = blogService.getBlog(id);
        blog.init(); //把tagIds处理成字符串

        model.addAttribute("blog", blogService.getBlog(id));

        return "admin/blogs-input";
    }


    @PostMapping("/blogs/put")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }
        if(b == null){
            //新增失败
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        //attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }






}
