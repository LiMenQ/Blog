package com.lmq.dao;

import com.lmq.pojo.Blog;
import com.lmq.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/31 15:16
 */
//帮助查询
public interface IBlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    //List<Blog> findByType(Type type);
    @Query("select b from t_blog b where b.recommend = true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);



    @Query("select function('date_format',b.createTime,'%Y') from t_blog b group by function('date_format',b.createTime,'%Y') order by function('date_format',b.createTime,'%Y') desc ")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.createTime,'%Y') = ?1")
    List<Blog> findByYear(String year);


}
