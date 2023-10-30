package org.olympos.biz.impl;

import org.olympos.athena.cache.RedisHashCache;
import org.olympos.athena.cache.RedisPageSetCache;
import org.olympos.athena.cache.RedisSetCache;
import org.olympos.athena.cache.RedisValueCache;
import org.olympos.biz.DBTestBiz;
import org.olympos.dao.TestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TestBiz
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2022-01-16 09:04
 */
@Service
public class DBTestBizImpl implements DBTestBiz {

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private RedisHashCache redisHashCache;
    @Autowired
    private RedisPageSetCache redisPageSetCache;
    @Autowired
    private RedisSetCache redisSetCache;
    @Autowired
    private RedisValueCache redisValueCache;

    public String select(@RequestParam Long id) {
        return testDAO.selectTest( id);
    }

    public String select2(@RequestParam Long id) {
        return testDAO.selectTest2( id);
    }
}
