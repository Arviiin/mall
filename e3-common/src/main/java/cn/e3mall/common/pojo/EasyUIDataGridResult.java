package cn.e3mall.common.pojo;


import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable{//要实现网络传输就需要序列化和反序列号，所以就要实现Serializable接口
 

	private long total;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
