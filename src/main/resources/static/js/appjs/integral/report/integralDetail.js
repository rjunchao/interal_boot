	
	var prefix = "/integral/report/";
	
	/**
	 * 
	 * @param type 1：积分添加，2：积分兑换，3：vip积分赠送
	 * @returns
	 */
	function searchIntegralDetail(type){
		let url = prefix + "add/list/" + hiddenFlag;
		if( 2 == type){url = prefix + "sub/list/" + hiddenFlag;}
		if( 3 == type){url = prefix + "vip/list/" + hiddenFlag;}
		let grid = $("#integralDetailGrid");
		let ops = grid.datagrid("options");
		ops.url=url;//设置URL
		//封装参数
		let customerIdcard=$("#customerIdcard").val();
		let startDate=$("#startDate").val();
		let endDate=$("#endDate").val();
		if(1 == hiddenFlag){
			if(customerIdcard == null || customerIdcard == ''){
				layer.msg("请输入身份证");
				return ;
			}
		}
		grid.datagrid("load", {
			page:ops.pageNumber,
			rows:ops.pageSize,
			customerIdcard:customerIdcard,
			startDate:startDate,
			endDate:endDate,
		});
	}
	
	/**
	 * 导出 
	 * @param type 1：积分添加，2：积分兑换，3：vip积分赠送
	 * @returns
	 */
	function detailExport(type){
		let params = $("#paramFrom").serialize();
		window.open(prefix + "/export/" + hiddenFlag + "/"+type+"?" + params);
	}