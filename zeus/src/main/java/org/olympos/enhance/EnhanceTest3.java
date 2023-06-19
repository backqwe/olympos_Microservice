package org.olympos.enhance;

import org.olympos.cloud.server.handler.enhanceHandler.ApiFaceEnhanceHandler;
import org.olympos.cloud.server.handler.paramHandler.PostProcessorParam;
import org.olympos.cloud.server.handler.paramHandler.PreProcessorParam;
import org.springframework.stereotype.Component;

/**
 * EnhanceTest3
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2021-08-13 17:32
 */
@Component
public class EnhanceTest3 implements ApiFaceEnhanceHandler {

    @Override
    public void beforeHandle(PreProcessorParam param) {
        System.out.println("####################### before test start ######################");
        System.out.println("####################### before test ! ##########################");
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

    @Override
    public void afterHandle(PostProcessorParam param) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test start %%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test ! %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test ! %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% " + param.getResults() + " %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% " + param.getResultType() + " %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% " + param.getParamTypes() + " %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% " + param.getParams() + " %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test ! %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test ! %%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% after test end %%%%%%%%%%%%%%%%%%%%%%%%");
    }

    public boolean afterSkip() {
        return false;
    }

    public int getOrder() {
        return 3;
    }
}
