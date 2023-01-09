package com.lmq.service;

import com.lmq.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @author 李孟琪
 * @version 1.0
 * @date 2022/3/30 14:48
 */
public interface ITypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);  //分页查询

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    List<Type> listType();

    List<Type> listTypeTop(Integer size); //指定展示多少数据

}
