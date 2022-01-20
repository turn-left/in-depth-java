package com.ethen.mybatis.model;

import lombok.Data;

@Data
public class Blog {
    private int id;
    private String title;
    private String content;
    private String author;
    private String datetime;
}
