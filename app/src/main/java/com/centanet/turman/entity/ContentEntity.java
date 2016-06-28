package com.centanet.turman.entity;

import java.util.List;

/**
 * Created by diaoqf on 2016/6/28.
 */
public class ContentEntity<T extends BaseEntity> extends BaseEntity {
    public int records;
    public String listType;
    public int page;
    public List<T> rows;
    public int total;
}
