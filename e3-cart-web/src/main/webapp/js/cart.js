var CART = {
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");// $(this):把当前节点包装成jQuery对象,这样才能用jQUery方法,this就这这个节点.increment
			_thisInput.val(eval(_thisInput.val()) + 1);
			//把请求发送到服务端,服务端+1再写回来.主要处理cookie
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){//ajax请求响应一个json
				CART.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				CART.refreshTotalPrice();
			});
		});
		/*$(".itemnum").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				CART.refreshTotalPrice();
			});
		});*/
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".itemnum").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$("#allMoney2").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '¥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	CART.itemNumChange();
});