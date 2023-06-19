package org.olympos.server;

import org.olympos.entity.ComplexBeanTest;
import org.olympos.entity.DubboTest;

import java.util.List;

/**
 * The interface Dubbo multi param service.
 */
public interface DubboMultiParamService {

    /**
     * Find by ids and name dubbo test.
     * body: {"ids":["1232","456"],"name":"hello world"}
     *
     * @param ids  the ids
     * @param name the name
     * @return the dubbo test
     */
    DubboTest findByIdsAndName(List<Integer> ids, String name);

    /**
     * Find by array ids and name dubbo test.
     * body :{"ids":[123,4561],"name":"hello world"}
     *
     * @param ids  the ids
     * @param name the name
     * @return the dubbo test
     */
    DubboTest findByArrayIdsAndName(Integer[] ids, String name);

    /**
     * Find by string array dubbo test.
     * body :{"ids":["1232","456"]}
     *
     * @param ids the ids
     * @return the dubbo test
     */
    DubboTest findByStringArray(String[] ids);

    /**
     * Find by list id dubbo test.
     * body :{"ids":["1232","456"]}
     *
     * @param ids the ids
     * @return the dubbo test
     */
    DubboTest findByListId(List<String> ids);

    /**
     * Batch save dubbo test.
     * body :{"dubboTestList":[{"id":"123","name":"xiaoyu"},{"id":"456","name":"myth"}]}
     *
     * @param dubboTestList the dubbo test list
     * @return the dubbo test
     */
    DubboTest batchSave(List<DubboTest> dubboTestList);

    /**
     * Batch save and name and id dubbo test.
     * body: {"dubboTestList":[{"id":"123","name":"xiaoyu"},{"id":"456","name":"myth"}],"id":"789","name":"ttt"}
     *
     * @param dubboTestList the dubbo test list
     * @param id            the id
     * @param name          the name
     * @return the dubbo test
     */
    DubboTest batchSaveAndNameAndId(List<DubboTest> dubboTestList, String id, String name);

    /**
     * Save complex bean test dubbo test.
     * body : {"dubboTest":{"id":"123","name":"xiaoyu"},"idLists":["456","789"],"idMaps":{"id2":"2","id1":"1"}}
     *
     * @param complexBeanTest the complex bean test
     * @return the dubbo test
     */
    DubboTest saveComplexBeanTest(ComplexBeanTest complexBeanTest);

    /**
     * Save complex bean test and name dubbo test.
     * body : {"complexBeanTest":{"dubboTest":{"id":"123","name":"xiaoyu"},"idLists":["456","789"],"idMaps":{"id2":"2","id1":"1"}},"name":"xiaoyu"}
     * @param complexBeanTest the complex bean test
     * @param name            the name
     * @return the dubbo test
     */
    DubboTest saveComplexBeanTestAndName(ComplexBeanTest complexBeanTest, String name);
}
