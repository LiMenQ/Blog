package com.lmq.dao;

import com.lmq.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/4/4 20:22
 */
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogId(Long blogId, Sort sort);
}
