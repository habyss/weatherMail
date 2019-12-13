package com.test.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: JsonResult 
* @Description: 接口统一返回工具类，数据格式为JSON，每个接口默认返回RESULT,DESCRIPTION,SYSTEMTIME,
* 具体业务信息放在INFO中
* @author xiaotian.zhang 
* @date 2017年11月8日 下午3:21:06 
*
 */
public class JsonResult {
	private static final Logger logger  = LoggerFactory.getLogger(JsonResult.class);

	private static <T> BaseAO getMap(int code, String msg, T t){
		BaseAO baseAO = new BaseAO();
		try {
			baseAO.setResult(code);
			baseAO.setMsg(msg);
			baseAO.setSystemTime(System.currentTimeMillis());
			baseAO.setInfo(t);
			ObjectMapper objectMapper = new ObjectMapper();
			logger.info(objectMapper.writeValueAsString(baseAO));
		} catch (JsonProcessingException e) {
			logger.info("JSON出错");
		}
		return baseAO;
	}
	
	public static <T> BaseAO successMap(String msg,T T){
		return getMap(Constant.SUCCESS_CODE, msg,T);
	}
	public static <T> BaseAO successMap(String msg){
		return getMap(Constant.SUCCESS_CODE, msg,null);
	}
	public static <T> BaseAO errorMap(String msg,T T){
		return getMap(Constant.ERROR_CODE, msg,T);
	}
	public static <T> BaseAO errorMap(String msg){
		return getMap(Constant.ERROR_CODE, msg,null);
	}

	public static <T> BaseAO failureMap(String msg,T t){
		return getMap(Constant.FAILURE_CODE, msg,t);
	}
	public static <T> BaseAO failureMap(String msg){
		return getMap(Constant.FAILURE_CODE, msg,null);
	}

	public static <T> BaseAO authFailureMap(String msg,T t){
		return getMap(Constant.AUTH_FAILURE, msg,t);
	}
	public static <T> BaseAO authFailureMap(String msg){
		return getMap(Constant.AUTH_FAILURE, msg,null);
	}

	public static <T> BaseAO noLoadingMap(String msg){
		return getMap(Constant.NO_LOADING, msg,null);
	}
}
