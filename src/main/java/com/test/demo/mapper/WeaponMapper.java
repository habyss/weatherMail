package com.test.demo.mapper;

import com.test.demo.entity.Weapon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kun.han on 2019/5/28 10:19
 */
@Mapper
public interface WeaponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(List<Weapon> list);

    int insertSelective(Weapon record);

    Weapon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Weapon record);

    int updateByPrimaryKey(Weapon record);
}