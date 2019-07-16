package com.test.demo.mapper.test;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.test.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kun.han on 2019/7/5 16:15
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByName(User record);


}