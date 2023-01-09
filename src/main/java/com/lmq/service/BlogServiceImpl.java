package com.lmq.service;

import com.lmq.NotFoundException;
import com.lmq.dao.IBlogRepository;
import com.lmq.pojo.Blog;
import com.lmq.pojo.Type;
import com.lmq.sup.BlogQuery;
import com.lmq.util.MarkdownUtils;
import com.lmq.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/31 15:15
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IBlogRepository repository;


    @Override
    public Blog getBlog(Long id) {
        //Markdown转换成HTML
        return repository.getById(id);
    }

    @Transactional
    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog =  repository.getById(id);

        Blog tempBlog = new Blog();
        BeanUtils.copyProperties(blog,tempBlog);
        String content = tempBlog.getContent();
        tempBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        repository.updateViews(id);

        return tempBlog;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        //根据条件动态的组合去查询
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(!("".equals(blog.getTitle())) && blog.getTitle() != null){
                    //如果title有值 按照title
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));//模糊查询
                }
                //按照专栏确定查询 空指针
                //这个type是没有的****************************************
                //拿不到type blog.getType()!=null && blog.getType().getId() != null
                if(blog.getTypeId() != null){
                    predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                //按照是否推荐查询
                if(blog.isRecommend()){
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return repository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return repository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = repository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,repository.findByYear(year));
        }

        return map;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //新增进行初始化
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);  //浏览次数初始设置为0
        }else{
            blog.setUpdateTime(new Date());  //仅仅改修改时间就行了
        }


        return repository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = repository.getById(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }

        //解决有些字段是空的问题
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog)); //复制值
        b.setUpdateTime(new Date());
        return repository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long countBlog() {
        return repository.count();
    }


}
