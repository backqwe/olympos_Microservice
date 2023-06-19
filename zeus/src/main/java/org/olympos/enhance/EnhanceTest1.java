package org.olympos.enhance;

import org.olympos.cloud.server.handler.enhanceHandler.ApiFaceEnhanceHandler;
import org.olympos.cloud.server.handler.paramHandler.PreProcessorParam;
import org.springframework.stereotype.Component;

/**
 * EnhanceTest1
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2021-08-13 17:32
 */
@Component
public class EnhanceTest1 implements ApiFaceEnhanceHandler {

    @Override
    public void beforeHandle(PreProcessorParam param) {
        System.out.println("####################### before test start ######################");
        System.out.println("####################### before test ! ##########################");
        System.out.println("####################### before test ! ##########################");
        System.out.println("####################### " + param.getParamTypes() + " ##########################");
        System.out.println("####################### " + param.getParams() + " ##########################");

        try {
            param.updateParam( "id", String.valueOf(Long.parseLong( (String)param.getParams().get( "id")) + 1));
        } catch (NumberFormatException e) {
            System.out.println( "id 参数 非数字！");
        }

        System.out.println("####################### before test ! ##########################");
        System.out.println("####################### " + param.getParamTypes() + " ##########################");
        System.out.println("####################### " + param.getParams() + " ##########################");
        System.out.println("####################### before test ! ##########################");
        System.out.println("####################### before test ! ##########################");
        System.out.println("####################### before test end ########################");
    }

    public boolean beforeSkip() {
        return false;
    }

    public int getOrder() {
        return 1;
    }
}
