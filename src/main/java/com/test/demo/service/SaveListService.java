package com.test.demo.service;

import com.test.demo.entity.Weapon;
import com.test.demo.mapper.WeaponMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author kun.han on 2019/6/5 9:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SaveListService {

    @Resource
    private WeaponMapper weaponMapper;

    public void testSaveList(){
        ArrayList<Weapon> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Weapon weapon = new Weapon();
            weapon.setName("save"+i);
            weapon.setDescription("testSave"+i);
            list.add(weapon);
        }
        weaponMapper.insert(list);
    }
}
