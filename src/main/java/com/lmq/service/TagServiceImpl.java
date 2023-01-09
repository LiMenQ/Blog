package com.lmq.service;

import com.lmq.NotFoundException;
import com.lmq.dao.ITagRepository;
import com.lmq.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/31 12:10
 */
@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    private ITagRepository tagRepository;


    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getById(id);
        if(t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t); //复制值
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {

        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!("".equals(ids)) && ids!=null){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length ; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }





}
