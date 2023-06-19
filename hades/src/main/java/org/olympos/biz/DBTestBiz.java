package org.olympos.biz;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * DBTestBiz
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-05-28 12:17
 */
public interface DBTestBiz {

    String select(@RequestParam Long id);

    String select2(@RequestParam Long id);
}
