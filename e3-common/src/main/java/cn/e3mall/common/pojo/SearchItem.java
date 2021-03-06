package cn.e3mall.common.pojo;

import java.io.Serializable;

public class SearchItem implements Serializable{

	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	
	//加这个方法相当于多了一个images属性,当访问这个属性的时候,默认会调用getImages方法,会得到一个数组,我只取一张
	public String[] getImages() {//用来解决有多张图片的,搜索问题
		if (image != null && !"".equals(image)) {
			String[] strings = image.split(",");
			return strings;
			
			
		}
		return null;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}


}
