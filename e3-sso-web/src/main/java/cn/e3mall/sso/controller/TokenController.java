package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.Result;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

/**
 * 根据token查询用户信息Controller
 * <p>Title: TokenController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年7月5日
 * @version 1.0
 */
@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	/*//@RequestMapping("/user/token/{token}")   这样响应的为Content-Type: text/html  我们想告诉他返回的是json,可以如下
	@RequestMapping(value="/user/token/{token}",//Content-Type: application/json;charset=UTF-8   两种解析的时候字体大小等不一样
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE原始写法"application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback){
		E3Result e3Result = tokenService.getUserByToken(token);
		//响应结果之前,判断是否为jsonp请求
		if (StringUtils.isNoneBlank(callback)) {
			//把结果封装成一个js语句响应
			return callback + "(" +JsonUtils.objectToJson(e3Result) + ");";
		}
		return JsonUtils.objectToJson(e3Result);
	}*/
	
	
	//高级写法  spring4.1以后支持
		@RequestMapping("/user/token/{token}")
		@ResponseBody							//一个从路径中取,一个从参数取
		public Object getUserByToken(@PathVariable String token, String callback){
			E3Result e3Result = tokenService.getUserByToken(token);
			//响应结果之前,判断是否为jsonp请求
			if (StringUtils.isNoneBlank(callback)) {
				//把结果封装成一个js语句响应
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return e3Result;
		}
}
