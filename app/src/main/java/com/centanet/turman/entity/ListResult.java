package com.centanet.turman.entity;

/**
 * Created by diaoqf on 2016/6/28.
 */
public class ListResult<T extends BaseEntity> extends BaseListResult {
    public ContentEntity<T> content;
    public String empId;
    public String token;
}
