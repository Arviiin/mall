package cn.e3mall.order.pojo;

import java.io.Serializable;
import java.util.List;

import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable{//因为要网络传输所以要实现序列化接口
	//其他字段直接继承父类TbOrder中的
	private List<TbOrderItem> orderItems;//注意所有属性都要和jsp中字段一模一样,如果原来定义好的pojo与jsp不一样,建议更改jsp中
	private TbOrderShipping orderShipping;//不建议更改pojo,因为可以别人已经用了pojo,可能会造成错误.
	
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
