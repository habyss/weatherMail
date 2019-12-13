package com.test.demo.mapper.wf;
import java.util.Collection;

import com.test.demo.entity.Content;
import com.test.demo.entity.Title;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author kun.han on 2019/12/12 13:35
 */
@Mapper
public interface TitleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Title record);

    int insertSelective(Title record);

    Title selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Title record);

    int updateByPrimaryKey(Title record);

    int updateBatch(List<Title> list);

    int batchInsert(@Param("list") List<Title> list);

    int updateBatchByContentId(List<Title> list);

    List<Title> selectAll();

    int deleteByContentIdIn(@Param("contentIdCollection")Collection<Integer> contentIdCollection);

    List<Title> selectAllByTitle(@Param("title")String title);






}