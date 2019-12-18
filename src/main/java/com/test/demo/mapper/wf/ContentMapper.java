package com.test.demo.mapper.wf;

import com.test.demo.entity.Content;

import java.util.Collection;
import java.util.List;

import com.test.demo.entity.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author kun.han on 2019/12/18 13:33
 */
@Mapper
public interface ContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKey(Content record);

    int updateBatch(List<Content> list);
    int updateBatchs(List<Title> list);

    int batchInsert(@Param("list") List<Content> list);

    int deleteByTitleIdIn(@Param("titleIdCollection") Collection<Long> titleIdCollection);

    int deleteByContentIdIn(@Param("contentIdCollection")Collection<Integer> contentIdCollection);


}