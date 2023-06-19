package org.olympos.dao.mapper.db2;

import org.apache.ibatis.annotations.Param;

/**
 * TestMapper
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2022-01-16 07:53
 */
public interface TestMapper2 {
    String selectTest(@Param("id") Long id);
}
