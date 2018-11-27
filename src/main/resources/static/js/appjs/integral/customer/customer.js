	var prefix="/integral/customer/";
	$(function(){
		configLoad();
	});
	function configLoad(){
		let url = prefix + "list/" + hiddenFlag;
		if(1 == hiddenFlag){
			url = null;
		}
		var grid = $("#customerTab").bootstrapTable({
			method:"post",
			url:url,
			iconSize : 'outline',
			toolbar:'#customerToolbar',//操作按钮
			striped : true, // 隔行变色
			pageSize:10,
			pageNumber:1,
			dataType:"json",
			singleSelect:true,
			showColumns:true,
			sidePagination:"server",//分页
			queryParams : function(params) {
				
				return {
					// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					limit : params.limit,
					offset : params.offset,
					customerName : $('#customerName').val(),
					customerIdcard : $('#customerIdcard').val(),
					customerPhone : $('#customerPhone').val()
				};
			},
			columns:[
				{checkbox: true},
				{field: "customerName", title:"姓名"},
				{field: "customerIdcard", title:"身份证"},
				{field: "customerPhone", title:"电话号码"},
				{field: "nowUsablePntegral", title:"积分"},
				{field: "def1", title:"VIP"},
				{field: 'def2',title : '备注'},
				{field: 'creater',title : '创建人'},
				{field: 'createTime',title : '创建时间'},
				{field: 'modifier',title : '修改人'},
				{field: 'modifiedTime',title : '修改时间'},
				{
					title:'操作',
					field:'pkCustomerInfo',
					align:'center',
					formatter:function(value, row, index){
						//删除，修改
						let e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
							+ value
							+ '\')"><i class="fa fa-edit"></i></a> ';
						let d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
							+ value
							+ '\')"><i class="fa fa-remove"></i></a> ';
						return e ;
					}
				}
			]
		});
	}
	
	/**
	 * 客户导出
	 * @returns
	 */
	function exportCustomer(){
		
	}
	
	/**
	 * 积分添加
	 * @returns
	 */
	function addIntegral(){
		
	}
	
	/**
	 * 积分赠送
	 * @returns
	 */
	function vipIntegral(){
		
	}
	
	/**
	 * 积分兑换
	 * @returns
	 */
	function subIntegral(){
		
	}
	
	function reloadIntegralCustomer(){
		let customerName = $('#customerName').val();
		let customerIdcard = $('#customerIdcard').val();
		let customerPhone = $('#customerPhone').val();
		if((customerName == null || customerName == '')
			&& (customerIdcard == null || customerIdcard == '')
			&& (customerPhone == null || customerPhone == '')
			){
			layer.msg("至少输入一个查询条件进行查询");
			return null;
		}
		$("#customerTab").bootstrapTable('refresh');
	}
	
	/**
	 * 
	 * @param type
	 * @returns
	 */
	function edit(id){
		layer.open({
			type:2,
			title:"添加积分类型",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '320px' ],//宽，高
			content : '/integral/config/edit/1/' + id // iframe的url
		});
	}
	function remove(id){
		layer.open({
			type:2,
			title:"添加积分类型",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '320px' ],//宽，高
			content : '/integral/config/edit/1/' + id // iframe的url
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
			title:"添加积分类型",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '320px' ],//宽，高
			content : '/integral/config/add/1'// iframe的url
		});
	}
	

	/**
	 * 批量删除不区分类型
	 * @returns
	 */
	function batchRemove(){
		
		//获取选择的行数据
		let params = $('#customerTab').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
		if(params != null && params.length > 0){
			layer.confirm("确定要删除所选择的配置项", {btn:["确定", "取消"]}, function(){
				let ids = new Array()
				for(let i = 0, len = params.length; i < len; i++){
					ids[i] = params[i].pkGdIntegralConfig;
				}
				
				$.ajax({
					url:"/integral/config/batchRemove",
					data:{'ids':ids, 'type':1},
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
	
	