package com.test.demo.mapper;

import com.test.demo.entity.Gamer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kun.han on 2019/5/28 10:18
 */
@Mapper
public interface GamerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gamer record);

    int insertSelective(Gamer record);

    Gamer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gamer record);

    int updateByPrimaryKey(Gamer record);
}