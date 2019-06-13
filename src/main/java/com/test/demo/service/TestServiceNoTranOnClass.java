package com.test.demo.service;

import com.test.demo.entity.Gamer;
import com.test.demo.entity.Weapon;
import com.test.demo.mapper.GamerMapper;
import com.test.demo.mapper.WeaponMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/5/28 10:30
 */
@Service
public class TestServiceNoTranOnClass {
    @Resource
    private GamerMapper gamerMapper;

    @Resource
    private WeaponMapper weaponMapper;

    @Transactional(rollbackFor = Exception.class)
    public void testTranGamer(){
        Gamer gamer = new Gamer();
        gamer.setName("test");
        gamer.setSex(1);
        gamerMapper.insert(gamer);

        testTranWeapon();

    }

    @Transactional(rollbackFor = Exception.class)
    public void testTranWeapon(){
        Weapon weapon = new Weapon();
        weapon.setName("test");
        weapon.setDescription("testDesc");
//        weaponMapper.insert(weapon);
        int i = 1/0;
    }

    public void test(){
        testTranGamer();
        int i = 1/0;
        testTranWeapon();
    }

    public void test2(){
        testTranWeapon();
        int i = 1/0;
        testTranGamer();
    }
}
