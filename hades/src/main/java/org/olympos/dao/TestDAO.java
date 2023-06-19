package org.olympos.dao;

import jakarta.annotation.Resource;
import org.olympos.dao.mapper.db1.TestMapper1;
import org.olympos.dao.mapper.db2.TestMapper2;
import org.springframework.stereotype.Repository;


/**
 * TestDAO
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2022-01-16 07:59
 */
@Repository
public class TestDAO {

    @Resource
    private TestMapper1 masterTestMapper;

    @Resource
    private TestMapper2 slaveTestMapper;

    public String selectTest(Long id) {
        return masterTestMapper.selectTest( id);
    }

    public String selectTest2(Long id) {
        return slaveTestMapper.selectTest( id);
    }
}
