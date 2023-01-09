package com.lmq.web;

import com.lmq.pojo.Type;
import com.lmq.service.IBlogService;
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

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/7 14:24
 */
@Controller
public class TypeController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IBlogService blogService;


    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 50, sort = {"createTime"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(9999);
        if(id == -1){
            id = types.get(0).getId();  //拿到第一个
        }


        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);

        return "types";
    }


}
