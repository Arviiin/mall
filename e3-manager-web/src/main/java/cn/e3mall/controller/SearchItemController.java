package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * 导入商品数据到索引库
 * <p>Title: SearchItemController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年6月21日
 * @version 1.0
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")//post请求可以加上也可以不加,jsp已经定义好了.
	@ResponseBody
	public E3Result importItemList(){
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;
	}
}
