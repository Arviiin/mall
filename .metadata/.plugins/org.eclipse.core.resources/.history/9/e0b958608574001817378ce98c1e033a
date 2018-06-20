package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * 首页展示Controller
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年6月16日
 * @version 1.0
 */
@Controller
public class IndexController {

	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@Autowired
	private ContentService contentService;
	/**
	 * 这个方法就可以了理解为一个Handler
	 * <p>Title: showIndex</p>
	 * <p>Description: </p>
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model model){
		//查询内容列表
		List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
		//将结果传递给页面 需要有参数Model  和freemarker很像
		model.addAttribute("ad1List", ad1List);
		
		return "index";
	}
	
}
