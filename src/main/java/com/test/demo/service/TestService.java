package com.test.demo.service;

import com.test.demo.entity.Gamer;
import com.test.demo.entity.Weapon;
import com.test.demo.mapper.GamerMapper;
import com.test.demo.mapper.WeaponMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/5/28 10:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TestService {

    @Resource
    private GamerMapper gamerMapper;

    @Resource
    private WeaponMapper weaponMapper;

    public void testTranGamer(){
        Gamer gamer = new Gamer();
        gamer.setName("test");
        gamer.setSex(1);
        gamerMapper.insert(gamer);

    }

    public void testTranWeapon(){
        Weapon weapon = new Weapon();
        weapon.setName("test");
        weapon.setDescription("testDesc");
       // weaponMapper.insert(weapon);
    }

    public void test(){
        testTranGamer();
        int i = 1/0;
        testTranWeapon();
    }
}
