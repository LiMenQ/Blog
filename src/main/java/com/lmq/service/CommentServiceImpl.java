package com.lmq.service;

import com.lmq.dao.ICommentRepository;
import com.lmq.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/4 20:21
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository repository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=Sort.by(Sort.Direction.DESC,"createTime");
        //并查集
        return repository.findByBlogId(blogId,sort);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
//        Long parentCommentId = comment.getParentComment().getId();
//        if (parentCommentId != -1){ //有父级
//            comment.setParentComment(repository.getById(parentCommentId));
//        }else {
//            comment.setParentComment(null);
//        }
        comment.setCreateTime(new Date());
        return repository.save(comment);
    }



}
