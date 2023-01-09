package com.lmq.service;

import com.lmq.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 14:48
 */
public interface ITagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);  //分页查询

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);


}
