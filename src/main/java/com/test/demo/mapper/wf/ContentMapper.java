package com.test.demo.mapper.wf;
import java.util.Collection;

import com.test.demo.entity.Content;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author kun.han on 2019/12/12 17:26
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

    int batchInsert(@Param("list") List<Content> list);

    int deleteByTitleIdIn(@Param("titleIdCollection")Collection<Long> titleIdCollection);





}