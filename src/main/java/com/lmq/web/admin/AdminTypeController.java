package com.lmq.web.admin;

import com.lmq.pojo.Blog;
import com.lmq.pojo.Type;
import com.lmq.service.IBlogService;
import com.lmq.service.ITypeService;
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
import java.util.List;


/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 15:17
 */
@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    @Autowired
    private ITypeService typeService;



    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.ASC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }






    @PostMapping("/types/put")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        //后端验证
        //System.out.println("test3=================================================");
        Type t1 = typeService.getTypeByName(type.getName());
        //System.out.println(t1 + "===================================================================");
        if(t1 != null){
            //System.out.println("test1=================================================4");
            //result.rejectValue("name","nameError","不能添加重复分类");
            attributes.addFlashAttribute("message","操作失败,不能重复添加");
            return "redirect:/admin/types";
        }

//        if(result.hasErrors()){
//            return "admin/types-input";
//        }

        Type t = typeService.saveType(type);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }



    //修改分类名称
    @PostMapping("types/put/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){

        //后端验证
        //System.out.println("test1=================================================");
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            //result.rejectValue("name","nameError","不能添加重复分类");
            attributes.addFlashAttribute("message","操作失败,不能重复添加");
            return "redirect:/admin/types";
            //System.out.println("test2=================================================");
        }

//        if(result.hasErrors()){
//            return "admin/types-input";
//        }

        Type t = typeService.updateType(id,type);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        //attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }





}
