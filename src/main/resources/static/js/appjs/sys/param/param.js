	//param页面URL前缀
	var prefix="/system/param";
	$(function(){
		//加载数据
		loadParam();
	});
	
	function loadParam(){
		//指定容器
		let paramTalbe = $("#paramTab").bootstrapTable({
			method:'get',//制定请求类型
			url:prefix + "/list",
			iconSize : 'outline',
			toolbar:'#paramToolbar',//操作按钮
			striped : true, // 隔行变色
			dataType:"json",//数据类型
			pagination:true,//显示分页信息
			singleSelect:false,//多选
			pageSize:10,
			pageNumber:1,
//			pageList:[10, 20, 50, 100],
//			search:true,//显示搜索框
			showColumns:true,//显示列
//			showRefresh:true,//显示刷新
			sidePagination:"server",//分页
			onLoadSuccess:function(data){
				layer.msg("加载成功");
			},
			queryParams : function(params) {
				return {
					// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					limit : params.limit,
					offset : params.offset,
					paramDesc : $('#searchParam').val()
				};
			},
			columns:[
				{
					checkbox:true
				},
				{
					field : 'paramCode',
					title : '参数编码'
				},
				{
					field : 'paramValue',
					title : '参数值'
				},
				{
					field : 'paramDesc',
					title : '参数描述'
				},
				{
					field : 'creater',
					title : '创建人'
				},
				{
					field : 'createTime',
					title : '创建时间'
				},
				{
					field : 'modifier',
					title : '修改人'
				},
				{
					field : 'modifiedTime',
					title : '修改时间'
				},
				{
					title : '操作',
					field : 'paramId',
					align : 'center',
					formatter : function(value, row, index) {
						let e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ value
								+ '\')"><i class="fa fa-edit"></i></a> ';
						let d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ value
								+ '\', \''+row.paramCode+'\')"><i class="fa fa-remove"></i></a> ';
						
						return e + d;
					}
				}
			]
		});
	}
	
	
	/**
	 * 刷新
	 * @returns
	 */
	function reloadParam(){
		$('#paramTab').bootstrapTable('refresh');
	}
	
	/**
	 * 删除
	 * @param id
	 * @returns
	 */
	function remove(id, code){
		layer.confirm("确定要删除参数["+code+"]",{
			btn : [ '确定', '取消' ]
		}, function(){
			//确定
			$.ajax({
				url:prefix + "/remove",
				data:{"id":id},
				type:'POST',
				success:function(msg){
					if(msg != null){
						layer.msg(msg.message);
						if(msg.flag){
							reloadParam();
						}
					}
				}
			});
		}, function(){});
	}
	
	/**
	 * 编辑
	 * @param id
	 * @returns
	 */
	function edit(id){
		layer.open({
			type: 2,//必须要加，不然调转不到后台
			title : '修改参数',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '400px' ],//宽，高
			content : prefix + '/edit/' + id // iframe的url
		});
	}
	
	/**
	 * 添加参数
	 * @returns
	 */
	function add(){
		layer.open({
			type: 2,//必须要加，不然调转不到后台
			title : '添加参数',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '400px' ],//宽，高
			content : prefix + '/add' // iframe的url
		});
	}
	
	/**
	 * 批量删除数据
	 * @returns
	 */
	function batchRemove(){
		//获取选择的行数据
		layer.msg("批量删除");
		
		let params = $('#paramTab').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
		if(params != null && params.length > 0){
			layer.confirm("确定要删除所选择的参数么？", {btn:["确定", "取消"]}, function(){
				let ids = new Array()
				for(let i = 0, len = params.length; i < len; i++){
					ids[i] = params[i].pkGdIntegralConfig;
				}
				$.ajax({
					url:prefix + "/batchRemove",
					data:{'ids':ids},
					type:"POST",
					success:function(msg){
						if(msg != null){
							layer.msg(msg.message);
							if(msg.flag){
								reloadParam();
							}
						}
					}
				});
				
			}, function(){});
		}
		
	}