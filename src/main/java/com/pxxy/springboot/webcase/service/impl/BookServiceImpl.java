package com.pxxy.springboot.webcase.service.impl;

import com.pxxy.springboot.webcase.entity.Book;
import com.pxxy.springboot.webcase.mapper.BookMapper;
import com.pxxy.springboot.webcase.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    public int save(Book book) {
        return bookMapper.save(book);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public int delete(Serializable id) {
        return bookMapper.delete(id);
    }

    @Override
    public List<Book> findAll() {
        return bookMapper.findAll();
    }

    @Override
    public Book findById(Serializable id) {
        return bookMapper.findById(id);
    }

    @Override
    public List<Book> findByKeyword(String keyword) {
        return bookMapper.findByKeywords(keyword);
    }

    @Override
    public Integer deleteSelected(List<Integer> idList) {
        return bookMapper.deleteSelected(idList);
    }
}
