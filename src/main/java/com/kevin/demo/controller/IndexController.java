package com.kevin.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevin.demo.common.constants.HttpConstant;
import com.kevin.demo.common.constants.HttpConstant.ReturnCode;
import com.kevin.demo.common.constants.HttpConstant.ReturnMessage;
import com.kevin.demo.common.scheduled.ScheduledSupport;
import com.kevin.demo.common.utils.DateStyle;
import com.kevin.demo.common.utils.DateUtil;
import com.kevin.demo.common.utils.MessageHandleUtil;
import com.kevin.demo.thread.MyThread;

@Controller
public class IndexController {
    
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    
    /**
     * <p>
     * Description: 启动延迟任务
     * </p>
     * 
     * @param key
     *            主键
     * @param delay
     *            延迟执行时间（秒）
     * @return
     */
    @RequestMapping(value = { "/", "/index" }, produces = HttpConstant.APPLICATION_JSON)
    @ResponseBody
    public String index(String key, String delay) {
        try {
            if (StringUtils.isBlank(key) || !NumberUtils.isNumber(delay)) {
                return MessageHandleUtil.buildResult(ReturnCode.CLIENT_INPUT_ERROR, ReturnMessage.CLIENT_INPUT_ERROR);
            }
            ScheduledSupport.scheduleDelaySeconds(key, new MyThread(key), Long.valueOf(delay));
            LOGGER.info("加入线程Key: {}, 将在 {} 执行该任务.", key, DateUtil.addSecond(
                    DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN), Integer.valueOf(delay)));
            LOGGER.info("线程池内线程数量: {}", String.valueOf(ScheduledSupport.getScheduledPoolSize()));
            return MessageHandleUtil.buildResult(ReturnCode.SUCCESS, ReturnMessage.SUCCESS);
        } catch (Exception e) {
            return MessageHandleUtil.buildServiceErrorResult();
        }
    }
    
    @RequestMapping(value = { "list" }, produces = HttpConstant.APPLICATION_JSON)
    @ResponseBody
    public String list() throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\list.json"))));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
