package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	
	@Autowired
	private JmsTemplate jmsTemplate;//tomcat会初始化Spring容器,这里直接注入就可以了.注意在配置文件中配置bean和用@Service,@Repository等注解方式的区别.
	
	//这里就不能直接根据类型来取了,因为在配置文件applicationContext-activemq.xml中配置的ActiveMQQueue
	//和ActiveMQTopic,都是Destination类型,这里我们用id取.所以就不用@Autowired,而用@Resource默认先用id再用类型
	//这里就先用topicDestination来找有没有id是这个的bean,没有再根据类型找
	@Resource
	private Destination topicDestination;
	
	
	
	@Override
	public TbItem getItemById(Long itemId) {
		//方法一根据主键查询
//		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
//		return tbItem;
		
		
		//方法二根据itemId查询
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}
	
	//E3Result要从service层传到表现层,需要通过网络传输,所以需要实现序列化
	@Override
	public E3Result addItem(TbItem item, String desc) {//jsp表单包含TbItem里部分的属性和TbItemDesc的desc
		//生成商品id									             为了方便就直接用String,而没有用TbItemDesc
		long itemId = IDUtils.genItemId();
		//补全item的属性(其余的都可以从表单中取得)  
		item.setId(itemId);
		item.setStatus((byte) 1);//商品状态，1-正常，2-下架，3-删除'
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据
		itemMapper.insert(item);
		//创建一个商品描述表对应的pojo对象.
		TbItemDesc itemDesc = new TbItemDesc();
		//补全属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		//向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		
		//发送商品添加消息
		
		//返回成功
		return E3Result.ok();
	}

}
