package com.pxxy.springboot.webcase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author yuyan
 * @version 1.0
 * @date 2020/5/11 11:08
 * @description 描述了
 */
public class Book implements Serializable {
    private Integer id;

    @NotEmpty(message = "{name.error.notnull}")
    //@NotEmpty(message = "图书名不能为空")
    //@Size(max = 25, message = "图书名长度不能超过25个字符")
    @Size(max = 25, message = "{name.error.max}")
    private String name;

    //@NotEmpty(message = "作者不能为空")
    @NotEmpty(message = "{author.error.notnull}")
    private String author;

    //@Min(value = 0, message = "价格必需大于0")
    @Min(value = 0, message = "{price.error.pattern}")
    private float price;

    @NotEmpty(message = "出版社不能为空")
    private String publisher;

    @Past(message = "出版日期不能晚于今天")
    @NotNull(message = "出版日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")  //此注解来自于：jackson-datatype-jsr310
    private LocalDate publishDate;

    //@NotBlank(message = "{coverurl.error.notnull}")
    private String coverUrl;

    public Book() {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", price=").append(price);
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", publish_date=").append(publishDate);
        sb.append(", cover_url='").append(coverUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
