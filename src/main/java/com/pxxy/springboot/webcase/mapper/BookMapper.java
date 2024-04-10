package com.pxxy.springboot.webcase.mapper;

import com.pxxy.springboot.webcase.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface BookMapper {

    int save(Book book);

    int update(Book book);

    int delete(Serializable id);

    List<Book> findAll();

    Book findById(Serializable id);

    List<Book> findByKeywords(@Param("keyword") String keyword);

    Integer deleteSelected(List<Integer> idList);
}
