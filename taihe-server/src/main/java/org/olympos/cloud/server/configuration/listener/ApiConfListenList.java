package org.olympos.cloud.server.configuration.listener;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * ApiConfListenList
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
@Component
@Data
public class ApiConfListenList implements Cloneable {

    private ConcurrentSkipListSet<String> listenList = new ConcurrentSkipListSet<>();

    public ApiConfListenList clone() throws CloneNotSupportedException {
        return (ApiConfListenList)super.clone();
    }
}
