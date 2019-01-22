package com.irongteng.persistence.mapper;

import java.util.List;

import dwz.dal.BaseMapper;

import org.springframework.stereotype.Repository;

import com.irongteng.persistence.beans.WebPage;

@Repository
public interface WebPageMapper extends BaseMapper<WebPage, Integer> {

    List<WebPage> findByTarget(String target);

}
