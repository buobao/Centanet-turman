package com.centanet.turman.entity;

import java.util.List;

/**
 * Created by diaoqf on 2016/6/28.
 */
public class ContentEntity extends BaseEntity {
    public int records;
    public String listType;
    public int page;
    public List<HouseEntity> rows;
    public int total;
}
