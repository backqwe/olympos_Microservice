package org.olympos.cloud.server.handler.enhanceHandler;

import org.olympos.cloud.server.handler.paramHandler.PostProcessorParam;
import org.olympos.cloud.server.handler.paramHandler.PreProcessorParam;

/**
 * ApiFaceEnhanceHandler
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-04-14 13:45
 */
public interface ApiFaceEnhanceHandler {

    default void beforeHandle(PreProcessorParam param) {}

    default boolean beforeSkip() {
        return false;
    }

    default void afterHandle(PostProcessorParam param) {}

    default boolean afterSkip() {
        return false;
    }

    default int getOrder() {
        return 99999;
    }
}
