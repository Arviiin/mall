package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

/**
 * 购物车处理Controller
 * <p>Title: CartController</p>
 * <p>Description: </p>
 * <p>Company: www.my.dhu.edu.cn</p>
 * @author LongLong
 * @data 2018年7月5日
 * @version 1.0
 */
@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	//String   表示返回逻辑视图
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num,//两个参数一个从路径中取,一个从参数中取
			HttpServletRequest request, HttpServletResponse response){//request取cookie, response写cookie
		//判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		//如果是登录状态,把购物车写入redis
		if (user != null) {
			//保存到服务端
			cartService.addCart(user.getId(), itemId, num);
			//返回逻辑视图
			return "cartSuccess";
		}
		//如果未登录使用cookie
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//判断商品在商品列表中是否存在
		boolean flag = false;//定义一个标志用来判断存不存在
		for (TbItem tbItem : cartList) {
			//如果存在数据相加
			if (tbItem.getId() == itemId.longValue()) {//tbItem.getId() == itemId  都是Long也就是对象,不能直接比,用.longValue
				flag = true;
				//找到商品,数量相加
				tbItem.setNum(tbItem.getNum() + num);
				//跳出循环
				break;
			}
		}
		//如果不存在,
		if (!flag) {
			//根据商品id查询商品信息,得到一个TbItem对象
			TbItem tbItem = itemService.getItemById(itemId);
			//设置商品数量
			tbItem.setNum(num);
			//取一张图片
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartList.add(tbItem);
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面
		return "cartSuccess";
	}
	
	/**
	 * 从cookie中取购物车列表的处理
	 * <p>Title: getCartListFromCookie</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//判断json是否为空
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		//把json转成商品列表  
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	/**
	 * 展示购物车列表
	 * <p>Title: showCartList</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request,HttpServletResponse response){
		
		//从cookie中取购物车列表(因为不管登录与否都要取,那么我们就把它放在上面)
		List<TbItem> cartList = getCartListFromCookie(request);
		
		//判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		//如果是登录状态
		if (user != null) {
			//从cookie中取购物车列表
			//如果不为空,把cookie中的购物车商品和服务端(redis)的购物车商品合并
			cartService.mergeCart(user.getId(), cartList);
			//cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		
		
		//未登录状态
		/*//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);*/
		//把列表传递给页面   要把cartList传递给页面,这里不用Model了,有Request,直接用好了
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "cart";
	}
	
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response){
		
		//判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		//如果是登录状态
		if (user != null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		//如果不是登录状态
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历商品列表找到对应的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回成功
		return E3Result.ok();
	}
	
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/cart/delete/{itemId}")
	//由代码<a id="cartDel" href="/cart/delete/${cart.id}.html">删除</a>可知
	//单击删除的时候,先跳转到href所指页面,再由"redirect:/cart/cart.html" 跳转回来
	public String updateCartNum(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response){
		
		//判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		//如果是登录状态
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历商品列表,找到要删除的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				//删除商品
				cartList.remove(tbItem);
				//跳出循环
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回逻辑视图
		return "redirect:/cart/cart.html";
	}
}
