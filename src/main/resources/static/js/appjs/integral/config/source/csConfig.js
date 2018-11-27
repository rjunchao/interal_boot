	var prefix="/integral/config";
	$(function(){
		
		configLoad();
	});
	function configLoad(){
		var grid = $("#csConfigTab").bootstrapTable({
			method:"get",
			url:prefix + "/list",
			iconSize : 'outline',
			toolbar:'#addConfigToolbar',//操作按钮
			striped : true, // 隔行变色
			pageSize:10,
			pageNumber:1,
			dataType:"json",
			pagination:true,//显示分页信息
			singleSelect:false,
			showColumns:true,
			sidePagination:"server",//分页
			queryParams : function(params) {
				
				return {
					// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					limit : params.limit,
					offset : params.offset,
					integralType: 4,
					integralTypeName : $('#searchParam').val()
				};
			},
			columns:[
				{checkbox: true},
				{field: "integralTypeName", title:"资金来源"},
				{field: 'creater',title : '创建人'},
				{field: 'createTime',title : '创建时间'},
				{field: 'modifier',title : '修改人'},
				{field: 'modifiedTime',title : '修改时间'},
				{
					title:'操作',
					field:'pkGdIntegralConfig',
					align:'center',
					formatter:function(value, row, index){
						let e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
							+ value
							+ '\')"><i class="fa fa-edit"></i></a> ';
//						let d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
//							+ value
//							+ '\', \''+row.paramCode+'\')"><i class="fa fa-remove"></i></a> ';
//				
						return e ;
					}
				}
			]
		});
	}
	function reloadIntegralConfig(){
		$("#csConfigTab").bootstrapTable('refresh');
	}
	
	/**
	 * 
	 * @param type
	 * @returns
	 */
	function edit(id){
		layer.open({
			type:2,
			title:"资金来源",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '260px' ],//宽，高
			content : '/integral/config/edit/4/' + id // iframe的url
		});
	}
	
	/**
	 * add页面
	 * @param type
	 * @returns
	 */
	function add(){
		layer.open({
			type:2,
			title:"资金来源",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '260px' ],//宽，高
			content : '/integral/config/add/4'// iframe的url
		});
	}
	

	/**
	 * 批量删除不区分类型
	 * @returns
	 */
	function batchRemove(){
		//获取选择的行数据
		let params = $('#csConfigTab').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
		if(params != null && params.length > 0){
			layer.confirm("确定要删除所选择的配置项", {btn:["确定", "取消"]}, function(){
				let ids = new Array()
				for(let i = 0, len = params.length; i < len; i++){
					ids[i] = params[i].pkGdIntegralConfig;
				}
				
				$.ajax({
					url:"/integral/config/batchRemove",
					data:{'ids':ids, 'type':4},
					type:"POST",
					success:function(msg){
						if(msg != null){
							layer.msg(msg.message);
							if(msg.flag){
								reloadIntegralConfig();
							}
						}
					}
				});
				
			}, function(){});
		}
	}
	
	