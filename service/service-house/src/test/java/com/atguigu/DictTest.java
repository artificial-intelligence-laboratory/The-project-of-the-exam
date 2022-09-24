package com.atguigu;

import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring整合junit4
 *  @RunWith(SpringRunner.class)
 *  只测试dao层，不需要其它配置文件
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class DictTest {

    @Resource
    private DictDao dictDao;

    @Test
    public void testFindListByParentId(){
        List<Dict> listByParentId = dictDao.findListByParentId(1L);
        for (Dict dict : listByParentId) {
            System.out.println("dict = " + dict);
        }
    }

}
