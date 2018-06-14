package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 页面跳转Controller
 * <p>Title: PageController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	
	@RequestMapping("/{page}")//请求的地址里面是什么我就给你返回哪个页面
	public String showPage(@PathVariable String page){
		return page;
	}
}
