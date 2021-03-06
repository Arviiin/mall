<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<!-- 内容分类管理的右键的菜单 ,可以看到为菜单又设置了onClick方法,当点击添加,重命名,删除时候就会触发onClick:menuHandler方法-->
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({//用id选择器拿到节点,基于这个节点初始化一个tree控件
								//用{}括起来的就是json,而json在js中看作对象,或者变量.
		url : '/content/category/list',//初始化tree请求的url
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){//右键弹出菜单事件,一般onxxx都是事件
            e.preventDefault();//e是事件对象,node是你在哪个节点上点击的右键
            $(this).tree('select',node.target);//右键后那个节点变蓝,呈选中状态
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY//在哪右键在哪显示出来菜单
            });
            //以上仅负责右键把菜单显示出来
        },
        onAfterEdit : function(node){//编辑结束会触发这个事件
        	var _tree = $(this);
        	if(node.id == 0){//id=0代表是新增的节点
        		// 新增节点,Ajax,post保存到数据库
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
function menuHandler(item){//item选的是哪个菜单
	var tree = $("#contentCategory");//先把tree拿到
	var node = tree.tree("getSelected");//从tree中找到选中的节点对象
	if(item.name === "add"){//如果点击的添加
		tree.tree('append', {//往当前节点添加子节点
            parent: (node?node.target:null),//parent即是当前节点
            data: [{
                text: '新建分类',
                id : 0,
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0);
		tree.tree("select",_node.target).tree('beginEdit',_node.target);
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete/",{id:node.id},function(){
					tree.tree("remove",node.target);
				});	
			}
		});
	}
}
</script>