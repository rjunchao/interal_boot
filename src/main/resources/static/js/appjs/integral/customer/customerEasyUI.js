	
	function searchCustomer(){
	
		let grid = $("#customDataGrid");
		let ops = grid.datagrid("options");
		let url = "/integral/customer/list/" + hiddenFlag;
		ops.url=url;
		//封装参数
		let customerName=$("#customerName").val();
		let customerIdcard=$("#customerIdcard").val();
		let customerPhone=$("#customerPhone").val();
		let startDate=$("#startDate").val();
		let endDate=$("#endDate").val();
		if(1 == hiddenFlag){
			if((customerName == null || customerName == '')
				&& (customerIdcard == null || customerIdcard == '')
				&& (customerPhone == null || customerPhone == '')
				){
				layer.msg("必须输入一个条件进行查询");
				return ;
			}
		}
		/*
			page:ops.pageNumber,
			rows: ops.pageSize,
		*/
		grid.datagrid("load", {
			page:ops.pageNumber,
			rows:ops.pageSize,
			customerName:customerName,
			customerIdcard:customerIdcard,
			customerPhone:customerPhone,
			startDate:startDate,
			endDate:endDate,
		});
	}
	/**
	 * 添加客户
	 * @returns
	 */
	function custom_add(){
		//add
		layer.open({
			type:2,
			title:"添加客户",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '250px' ],//宽，高
			content : '/integral/customer/add'// iframe的url
		});
	}
	/**
	 * @returns
	 */
	function customer_del(){
		let cs = $("#customDataGrid").datagrid("getSelections");
		if(cs == null || cs.length <= 0){
			layer.msg("请选择要修改的客户");
			return;
		}
		let customerName = cs[0].customerName;
		let pk = cs[0].pkCustomerInfo;
		$.messager.confirm('确认','确定删除 '+customerName+'吗？',function(action){
      	    if (action){
              	$.post("/integral/customer/delCust/" + pk, function(msg){
              		if(msg != null){
    					layer.msg(msg.message);
    					if(msg.flag){
    						//掉用父窗体的方法
    						searchCustomer();
    					}
    				}
          		});
      	    }
      	});
	}
	/**
	 * @returns
	 */
	function customer_edit(){
		//获取选择的客户
		let cs = $("#customDataGrid").datagrid("getSelections");
		if(cs == null || cs.length <= 0){
			layer.msg("请选择要修改的客户");
			return;
		}
		let id = cs[0].pkCustomerInfo;
		layer.open({
			type:2,
			title:"修改客户信息",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '250px' ],//宽，高
			content : '/integral/customer/edit/' + id + '/' + hiddenFlag// iframe的url
		});
	}
	/**
	 * @returns
	 */
	function vipHandle(){
		//获取选择的客户
		let cs = $("#customDataGrid").datagrid("getSelections");
		if(cs == null || cs.length <= 0){
			layer.msg("请选择要赠送积分的客户");
			return;
		}
		let vip = cs[0].def1;
		if("N" == vip){
			layer.msg("只能对VIP客户进行赠送积分");
			return;
		}
		let id = cs[0].pkCustomerInfo;
		layer.open({
			type:2,
			title:"积分赠送",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '400px', '250px' ],//宽，高
			content : '/integral/handle/index/3/' + id// iframe的url
		});
	}
	/**
	 * @returns
	 */
	function addHandle(){
		//获取选择的客户
		let cs = $("#customDataGrid").datagrid("getSelections");
		if(cs == null || cs.length <= 0){
			layer.msg("请选择要添加积分的客户");
			return;
		}
		let id = cs[0].pkCustomerInfo;
		layer.open({
			type:2,
			title:"积分添加",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '600px', '480px' ],//宽，高
			content : '/integral/handle/index/1/' + id// iframe的url
		});
		
	}
	/**
	 * @returns
	 */
	function subHandle(){
		//获取选择的客户
		let cs = $("#customDataGrid").datagrid("getSelections");
		if(cs == null || cs.length <= 0){
			layer.msg("请选择要兑换积分的客户");
			return;
		}
		let id = cs[0].pkCustomerInfo;
		layer.open({
			type:2,
			title:"积分兑换",
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '500px', '300px' ],//宽，高
			content : '/integral/handle/index/2/' + id// iframe的url
		});
	}