package org.olympos.cloud.server.handler.paramHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shenyu.common.constant.Constants;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

/**
 * ProcessorParam
 *
 * @Describe:
 * @Author Chasen Hao
 * @Date 2023-06-18 13:45
 */
public class PreProcessorParam extends BaseParam {

    public static PreProcessorParam getInstance(ServerWebExchange exchange) {
        return new PreProcessorParam( exchange);
    }

    private PreProcessorParam(ServerWebExchange exchange) {
        super.setExchange( exchange);

        // 初始化参数类型
        Object params = exchange.getAttributes().get( Constants.PARAM_TRANSFORM);
        Map<String, Object> requestParams = Objects.nonNull( params) ? new JacksonJsonParser().parseMap( params.toString()) : new LinkedHashMap<>();
        super.setRequestParams( requestParams);
        super.setParamTypes( new ArrayList<>( Arrays.asList( super.getParameterTypes().split(","))));
    }

    public void writeBackToExchange() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> requestParams = super.getRequestParams();
        if ( Objects.isNull( requestParams) || 0 == requestParams.size()) return;

        try {
            String json = mapper.writeValueAsString( requestParams);
            super.getExchange().getAttributes().put( Constants.PARAM_TRANSFORM, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateParam(String key, Object value) {
        Map<String, Object> requestParams = super.getRequestParams();
        if ( !requestParams.containsKey( key)) return;

        super.getRequestParams().put( key, value);
    }

    public Map<String, Object> getParams() {
        return super.getRequestParams();
    }

    public List<String> getParamTypes() {
        return super.getParamTypes();
    }
}
