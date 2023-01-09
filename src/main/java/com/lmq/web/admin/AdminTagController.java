package com.lmq.web.admin;

import com.lmq.pojo.Tag;
import com.lmq.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 15:17
 */
@Controller
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private ITagService tagService;


    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }






    @PostMapping("/tags/put")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

        //后端验证
        //System.out.println("test3=================================================");
        Tag t1 = tagService.getTagByName(tag.getName());  //不区分大小写
        //System.out.println(t1 + "===================================================================");
        if(t1 != null){
            //System.out.println("test1=================================================4");
            //result.rejectValue("name","nameError","不能添加重复分类");
            attributes.addFlashAttribute("message","操作失败,不能重复添加");
            return "redirect:/admin/tags";
        }

//        if(result.hasErrors()){
//            return "admin/tags-input";
//        }

        Tag t = tagService.saveTag(tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }



    //修改分类名称
    @PostMapping("tags/put/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){

        //后端验证
        //System.out.println("test1=================================================");
        Tag t1 = tagService.getTagByName(tag.getName());
        if(t1 != null){
            //result.rejectValue("name","nameError","不能添加重复分类");
            attributes.addFlashAttribute("message","操作失败,不能重复添加");
            return "redirect:/admin/tags";
            //System.out.println("test2=================================================");
        }

//        if(result.hasErrors()){
//            return "admin/tags-input";
//        }

        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        //attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }





}
