package org.olympos.service.impl;

import org.apache.shenyu.client.apache.dubbo.annotation.ShenyuDubboService;
import org.olympos.biz.DBTestBiz;
import org.olympos.entity.DubboTest;
import org.olympos.entity.ListResp;
import org.olympos.service.DubboTestService;
import org.apache.shenyu.client.apidocs.annotations.ApiModule;
import org.apache.shenyu.client.dubbo.common.annotation.ShenyuDubboClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Random;

/**
 * The type Dubbo service.
 */
@ShenyuDubboService
@ApiModule(value = "dubboTestService")
public class DubboTestServiceImpl implements DubboTestService {

    @Autowired
    private DBTestBiz dBTestBiz;

    @Override
    @ShenyuDubboClient(path = "/findFromDB1ById", desc = "Query by Id from db1")
    public DubboTest findFromDB1ById(final Long id, final String name) {
        DubboTest dubboTest = new DubboTest();
        dubboTest.setId( dBTestBiz.select( id));
        dubboTest.setName("hello world shenyu Apache, findById");
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/findFromDB2ById", desc = "Query by Id from db2")
    public DubboTest findFromDB2ById(final Long id, final String name) {
        DubboTest dubboTest = new DubboTest();
        dubboTest.setId( dBTestBiz.select2( id));
        dubboTest.setName("hello world shenyu Apache, findById");
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/findById2", desc = "Query by Id")
    public DubboTest findById2(final String id, final String name) {
        DubboTest dubboTest = new DubboTest();
        dubboTest.setId(id);
        dubboTest.setName("hello world shenyu Apache, findById");
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/findById", desc = "Query by Id")
    public DubboTest findById(final String id) {
        DubboTest dubboTest = new DubboTest();
        dubboTest.setId(id);
        dubboTest.setName("hello world shenyu Apache, findById");
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/findAll", desc = "Get all data")
    public DubboTest findAll() {
        DubboTest dubboTest = new DubboTest();
        dubboTest.setName("hello world shenyu Apache, findAll");
        dubboTest.setId(String.valueOf(new Random().nextInt()));
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/insert", desc = "Insert a row of data")
    public DubboTest insert(final DubboTest dubboTest) {
        dubboTest.setName("hello world shenyu Apache Dubbo: " + dubboTest.getName());
        return dubboTest;
    }

    @Override
    @ShenyuDubboClient(path = "/findList", desc = "Find list")
    public ListResp findList() {
        ListResp listResp = new ListResp();
        listResp.setTotal(1);
        listResp.setUsers( Arrays.asList( new DubboTest("1", "test")));
        return listResp;
    }
}
