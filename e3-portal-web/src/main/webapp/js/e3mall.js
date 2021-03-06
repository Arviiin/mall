var E3MALL = {
	checkLogin : function(){
		var _ticket = $.cookie("token");//取token信息
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8088/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){//响应成功,会返回一个data(json数据)
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到宜立方购物网！<a href=\"http://www.e3mall.cn/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){//当页面加载完成之后会执行下面的方法
	// 查看是否已经登录，如果已经登录查询登录信息
	E3MALL.checkLogin();//相当于静态类调用方法
});