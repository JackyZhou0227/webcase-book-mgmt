package com.pxxy.springboot.webcase.service;


import com.pxxy.springboot.webcase.entity.Book;

import java.io.Serializable;
import java.util.List;

public interface BookService {

    int save(Book book);

    int update(Book book);

    int delete(Serializable id);

    List<Book> findAll();

    Book findById(Serializable id);

    List<Book> findByKeyword(String keyword);

    Integer deleteSelected(List<Integer> idList);
}
