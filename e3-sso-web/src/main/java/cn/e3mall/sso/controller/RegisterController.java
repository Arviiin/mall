package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.RegisterService;

/**
 * 注册功能Controller
 * <p>Title: RegisterController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年7月3日
 * @version 1.0
 */
@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type){
		E3Result e3Result = registerService.checkData(param, type);
		return e3Result;
	}
}