package com.lmq.service;

import com.lmq.pojo.Comment;

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/4 20:18
 */
public interface ICommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);


}
