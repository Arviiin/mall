package cn.e3mall.search.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
	
	//用slf4j,这个相当于把log4j包装了一层,如果把log4j换了用别的日志jar包,也不会有影响
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);  

	@Override//dao向Service层抛,Service向Controller层抛,Controller向外抛,实际是抛给了SpringMVC框架了,SpringMVC框架会找一下有没有全局异常处理器,如果有就让它捕获
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//打印控制台
		ex.printStackTrace();
		//写日志  debug级别最低,info次之,error最高
		logger.debug("测试输出的日志.......");
		logger.info("系统发生异常了.....");
		logger.error("系统发生异常", ex);
		//发邮件(使用jmail工具包),发短信(第三方的webService)
		//展示友好的错误页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/error/exception");
		return modelAndView;
	}

}
