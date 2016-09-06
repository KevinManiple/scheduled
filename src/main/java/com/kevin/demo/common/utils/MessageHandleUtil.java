package com.kevin.demo.common.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kevin.demo.common.constants.HttpConstant;

public class MessageHandleUtil {
    
    public static String buildResult(int status, String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, status);
        json.put(HttpConstant.MSG, msg);
        json.put(HttpConstant.DATA, data);
        return json.toJSONString();
    }
    
    public static String buildResult(int status, String msg, Object data, SerializeFilter filter) {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, status);
        json.put(HttpConstant.MSG, msg);
        if (data instanceof Collection<?>) {
            json.put(HttpConstant.DATA, JSON.parseArray(JSON.toJSONString(data, filter)));
        } else {
            json.put(HttpConstant.DATA, JSON.parseObject(JSON.toJSONString(data, filter)));
        }
        return json.toJSONString();
    }
    
    public static String buildResult(int status, String msg, String key, Object value) {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, status);
        json.put(HttpConstant.MSG, msg);
        JSONObject result = new JSONObject();
        result.put(key, value);
        json.put(HttpConstant.DATA, result);
        return json.toJSONString();
    }
    
    public static String buildResult(int status, String msg, HashMap<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, status);
        json.put(HttpConstant.MSG, msg);
        JSONObject result = new JSONObject();
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> obj = it.next();
            result.put(obj.getKey(), obj.getValue());
        }
        json.put(HttpConstant.DATA, result);
        return json.toJSONString();
    }
    
    public static String buildResult(int status, String msg) {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, status);
        json.put(HttpConstant.MSG, msg);
        return json.toJSONString();
    }
    
    public static String buildServiceErrorResult() {
        JSONObject json = new JSONObject();
        json.put(HttpConstant.STATUS, HttpConstant.ReturnCode.SERVICE_ERROR);
        json.put(HttpConstant.MSG, HttpConstant.ReturnMessage.SERVICE_ERROR);
        return json.toJSONString();
    }
    
    public static String buildResultWithDateFormat(int status, String msg, Object data, String dateFormat) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(HttpConstant.STATUS, status);
        map.put(HttpConstant.MSG, msg);
        map.put(HttpConstant.DATA, data);
        return JSON.toJSONStringWithDateFormat(map, dateFormat, SerializerFeature.WriteNullStringAsEmpty);
    }
}