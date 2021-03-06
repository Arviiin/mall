package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;


/**
 * 内容分类管理Controller
 * <p>Title: ContentCatController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年6月17日
 * @version 1.0  
 */
@Controller
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentList(//有个事情说一下,不知道为什么一直报错,可能修改完content-service需要重新maven install
			@RequestParam(name = "id", defaultValue="0") long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		
		return list;
	}
	
	/**
	 * 添加分类节点
	 */
	@RequestMapping(value="/content/category/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId, String name){
		//调用服务添加节点
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}
}
