package com.test.demo.mapper.wf;
import java.util.Collection;

import com.test.demo.entity.WeatherConfig;
import com.test.demo.entity.model.WeatherConfigCommand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kun.han on 2019/6/17 9:43
 */
@Mapper
public interface WeatherConfigMapper {
    int insert(WeatherConfig record);

    int insertSelective(WeatherConfig record);

    WeatherConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeatherConfig record);

    int updateByPrimaryKey(WeatherConfig record);

    WeatherConfig getOneByType(@Param("type") String type);

    List<WeatherConfig> getAllByType(@Param("type") String type);

    WeatherConfig getSubject(@Param("type") String type);


    List<WeatherConfigCommand> getAllTest();

    int deleteByPrimaryKey(Long id);

    List<WeatherConfigCommand> getAllRepeat();

    int deleteByIdIn(@Param("idCollection")Collection<Long> idCollection);

    List<WeatherConfig> getAllByValue(@Param("value")String value);



}