package com.lmq.dao;

import com.lmq.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 15:04
 */
public interface ITagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);

}
