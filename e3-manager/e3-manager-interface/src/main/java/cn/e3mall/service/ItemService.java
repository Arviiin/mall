package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long itemId);
	EasyUIDataGridResult getItemList(int page,int rows);
	E3Result addItem(TbItem tbItem, String desc);
}
