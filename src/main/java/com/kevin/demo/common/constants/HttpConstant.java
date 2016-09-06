package com.kevin.demo.common.constants;

public class HttpConstant {
    
    /** JSON返回状态码 */
    public static final String STATUS = "status";
    
    /** JSON返回值 */
    public static final String MSG = "msg";
    
    /** JSON返回数据 */
    public static final String DATA = "data";
    
    /** JSON返回数据格式 */
    public static final String APPLICATION_JSON = "application/json;charset=utf-8";
    
    /** 数据库操作成功 1条记录更新/删除/修改成功 */
    public static final int OPERAT_ONE_RECORD_SUCCESS = 1;
    
    /** 编码格式 */
    public static final String UTF8 = "UTF-8";
    
    /**
     * 返回值
     * 
     * @author Kai.Zhao
     * @version 1.0
     * @see
     */
    public class ReturnMessage {
        
        public static final String QUERY_ERROR = "查询出错";
        
        public static final String INSERT_ERROR = "新增出错";
        
        public static final String UPDAT_EERROR = "更新出错";
        
        public static final String DELET_EERROR = "删除出错";
        
        public static final String SERVICE_TIME_OUT = "登录超时";
        
        public static final String SUCCESS = "操作成功";
        
        public static final String FAILURE = "操作失败";
        
        public static final String GET_DATA_FAILED = "获取数据失败";
        
        public static final String UNKNOWN_FAILURE = "未知错误";
        
        public static final String DUPLICATE_RECEIVE = "重复领取";
        
        public static final String OUT_OF_RANGE = "超出当日领取上限";
        
        public static final String SERVICE_ERROR = "服务器异常,请稍候再试!";
        
        public static final String CLIENT_INPUT_ERROR = "参数输入错误";
        
        public static final String COOKIE_IMEI_ERROR = "读取IMEI出错";
        
        public static final String GET_CONFIG_ERROR = "读取活动配置出错";
        
        public static final String REQUEST_ILLEGAL = "请求非法";
    }
    
    /**
     * 返回码
     * 
     * @author Kai.Zhao
     * @version 1.0
     * @see
     */
    public class ReturnCode {
        
        /** 成功 */
        public static final int SUCCESS = 200;
        
        /** 服务器异常 */
        public static final int SERVICE_ERROR = 500;
        
        /** 请求非法 */
        public static final int REQUEST_ILLEGAL = 501;
        
        /** 超时 */
        public static final int SERVICE_TIME_OUT = 502;
        
        /** 失败 */
        public static final int FAILURE = 600;
        
        /** 客户端输入错误 */
        public static final int CLIENT_INPUT_ERROR = 601;
    }
}
