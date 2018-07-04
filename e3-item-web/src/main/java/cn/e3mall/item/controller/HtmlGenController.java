package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态页面测试Controller
 * <p>Title: HtmlGenController</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Controller
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/genhtml")
	@ResponseBody//意义:直接响应浏览器,不走逻辑视图了,相当于响应response对象调用writer方法,直接往浏览器写返回的内容
	//不过如果返回类型是一个对象的话,就会有一个默认行为,会把对象变成json再响应
	//如果返回值类型是String的话,因为浏览器可以识别,所以不加转换,直接响应.兼容性更好
	public String genHtml() throws Exception {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		//加载模板对象
		Template template = configuration.getTemplate("hello.ftl");
		//创建一个数据集
		Map data = new HashMap<>();
		data.put("hello", 123456);
		//指定文件输出的路径及文件名
		Writer out = new FileWriter(new File("D:/temp/JavaEE32/freemarker/hell2.html"));
		//输出文件
		template.process(data, out);
		//关闭流
		out.close();
		
		return "OK";
	}
}
