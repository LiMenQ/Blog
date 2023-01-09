package com.lmq.service;

import com.lmq.pojo.Blog;
import com.lmq.pojo.Type;
import com.lmq.sup.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/31 15:11
 */
public interface IBlogService {

    Blog getBlog(Long id);

    Blog getBlogAndConvert(Long id);

    //分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String ,List<Blog>> archiveBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Long countBlog();








}
