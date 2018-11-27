
	var prefix = "/integral/handle/";
	
	$(function(){
		//显示资金来源23
		$("#isShowCap23").click(function(e){
			
			if($("#isShowCap23")[0].checked){
				$("#hiddenCapTr2").removeClass("hiddenTr");
				$("#hiddenCapTr3").removeClass("hiddenTr");
			}else{
				$("#hiddenCapTr2").addClass("hiddenTr");
				$("#hiddenCapTr3").addClass("hiddenTr");
			}
		});
		//显示资金来源23
		$("#isShowPeople23").click(function(e){
			
			if($("#isShowPeople23")[0].checked){
				$("#hiddenPeopTr2").removeClass("hiddenTr");
				$("#hiddenPeopTr3").removeClass("hiddenTr");
			}else{
				$("#hiddenPeopTr2").addClass("hiddenTr");
				$("#hiddenPeopTr3").addClass("hiddenTr");
			}
		});
	});
	
	/**
	 * 存款类型
	 * @param value
	 * @returns
	 */
	function calcConfigIntegral(value){
		let configArr = value.split("_");
		$("#configId").val(configArr[0]);
		$("#configTypeName").val(configArr[1]);
		$("#integralCalcNum").val(configArr[2]);
		calcAmt();
	}
	/**
	 * 存款类型
	 * @param value
	 * @returns
	 */
	function calcAmtIntegral(value){
		let amt = Number(value);
		if(amt > 0){
			$("#capitalSourceAmt1").val(value);
			$("#marketingPeopleAmt1").val(value);
			calcAmt();
		}else{
			layer.msg("金额必须大于0");
		}
	}
	
	/**
	 * 根据积分系数和金额计算得到积分
	 * @returns
	 */
	function calcAmt(){
		let amt = Number($("#depositReceiptAmt").val());
		let type = Number($("#integralCalcNum").val());
		//两个都有值
		if(amt > 0 && type > 0){
			$("#customerIntegral").val(Math.round(Number(amt * type)));
		}
	}
	
	/**
		校验营销金额
	*/
	function validPeopAmt(){
		//校验金额
		var amt1 = Number($("#marketingPeopleAmt1").val());
		//总金额
		var amt = Number($("#depositReceiptAmt").val());
		if($("#isShowPeople23")[0].checked){
			var amt2 = Number($("#marketingPeopleAmt2").val());
			var amt3 = Number($("#marketingPeopleAmt3").val());
			if(!(amt == (amt1 + amt2 + amt3))){
				if(!(amt == Number(parseFloat(amt1+ amt2 + amt3).toFixed(2)))){
					layer.msg("营销金额之和必须等于存单金额");
					return false;
				}
			}
			var name2 = $("#marketingPeople2").val();
			if(amt2 > 0){
				if(name2 == null || name2 == "" || name2 == undefined){
					layer.msg("已有营销金额，营销人必须输入");				
					return false;
				}else{
					if(name2.length != 8){
						//营销人长度
						layer.msg("营销人长度只能为8位");					
						return false;
					}
				}
			}else{
				if(name2.length > 0){
					layer.msg("已有营销人，营销金额必须输入");				
					return false;
				}
			}
			var name3 =  $("#marketingPeople3").val();
			if(amt3 > 0){
				if(name3 == null || name3 == "" || name3 == undefined){
					layer.msg("已有营销金额，营销人必须输入");					
					return false;
				}else{
					if(name3.length != 8){
						//营销人长度
						layer.msg("营销人长度只能为8位");					
						return false;
					}
				}
			}else{
				if(name3.length > 0){
					layer.msg("已有营销人，营销金额必须输入");				
					return false;
				}
			}
		}else{
			if(!(amt == (amt1))){
				layer.msg("营销人金额之和必须等于存单金额");
				return false;
			}
		}
		return true;
	}
	
	/**
		校验資金来源金额
	*/
	function validCapAmt(){
		//校验金额
		var amt1 = Number($("#capitalSourceAmt1").val());
		//总金额deposit_receipt_amt
		var amt = Number($("#depositReceiptAmt").val());
		if($("#isShowCap23")[0].checked){
			var amt2 = Number($("#capitalSourceAmt2").val());
			var amt3 = Number($("#capitalSourceAmt3").val());
			if(!(amt == (amt1 + amt2 + amt3))){
				if(!(amt == Number(parseFloat(amt1+ amt2 + amt3).toFixed(2)))){//防止四捨五入
					layer.msg("资金来源金额之和必须等于存单金额");
					return false;
				}
				
			}
			var name2 = $("#capitalSource2").val()
			if(amt2 > 0){
				if(name2 == null || name2 == "" || name2 == undefined){
					layer.msg("填了资金来源金额，资金来源必须选择");					
					return false;
				}
			}else{
				if(name2.length > 0){
					layer.msg("选了资金来源，资金来源金额必须输入");						
					return false;
				}
			}
			
			var name3 =  $("#capitalSource3").val()
			if(amt3 > 0){
				if(name3 == null || name3 == "" || name3 == undefined){
					layer.msg("填了资金来源金额，资金来源必须选择")				
					return false;
				}
			}else{
				if(name3.length > 0){
					layer.msg("选了资金来源，资金来源金额必须输入");				
					return false;
				}
			}
		}else{
			if(!(amt == (amt1))){
				layer.msg("资金来源金额之和必须等于存单金额");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 添加积分保存
	 * @returns
	 */
	function addSave(){
		//校验营销人金额
		if(!validPeopAmt()){
			return false;
		}
		//校验自己来源
		if(!validCapAmt()){
			return false;
		}
		var name =  $("#marketingPeople1").val();
		if(name.length != 8){
			//营销人长度
			layer.msg("营销人长度只能为8位");					
			return false;
		}
		var depositNum = $("#depositReceiptNum").val();
		if(!(depositNum.length == 3 || depositNum.length == 11 || depositNum.length == 10)){
			//存单号长度
			layer.msg("存单号长度是3位、10位、11位");					
			return false;
		}
		var depositAmt = Number($("#depositReceiptAmt").val());
		if(!(depositAmt > 0 && depositAmt < 100000000)){
			//金额
			layer.msg("金额必须小于1亿");					
			return false;
		}
		
		if(!$('#addHandleForm').form('validate')){
			layer.msg("红色框必须输入!");
			return ;
		}
		//表单对象
		let vo = {
				'customerIdcard':$("#customerIdcard").val(),
				'customerAccount':$("#customerAccount").val(),
				'duration':$("#duration").val(),
				'accountNumber':$("#accountNumber").val(),
				'depositReceiptNum':$("#depositReceiptNum").val(),
				'depositReceiptAmt':$("#depositReceiptAmt").val(),
				'configId':$("#configId").val(),
				'configTypeName':$("#configTypeName").val(),
				'integralCalcNum':$("#integralCalcNum").val(),
				'customerIntegral':$("#customerIntegral").val(),
				'capitalSource1':$("#capitalSource1").val(),
				'capitalSource2':$("#capitalSource2").val(),
				'capitalSource3':$("#capitalSource3").val(),
				'capitalSourceAmt1':$("#capitalSourceAmt1").val(),
				'capitalSourceAmt2':$("#capitalSourceAmt2").val(),
				'capitalSourceAmt3':$("#capitalSourceAmt3").val(),
				'marketingPeople1':$("#marketingPeople1").val(),
				'marketingPeople2':$("#marketingPeople2").val(),
				'marketingPeople3':$("#marketingPeople3").val(),
				'marketingPeopleAmt1':$("#marketingPeopleAmt1").val(),
				'marketingPeopleAmt2':$("#marketingPeopleAmt2").val(),
				'marketingPeopleAmt3':$("#marketingPeopleAmt3").val(),
				'remark':$("#remark").val()
		};
		if(vo.customerIntegral <= 0){
			layer.msg("积分必须大于0");
			return;
		}
		if(vo.depositReceiptAmt <= 0){
			layer.msg("金额必须大于0");
			return;
		}
		if(vo.accountNumber.length != 3){
			layer.msg("账号序号必须为3位");
			return;
		}
		
		let url = prefix + "addSave";
	//	let param = $("#vipHandleForm").serialize();
		$.ajax({
			url : url,
			type:"POST",
			data : vo, // 
			async : false,
			success : function(msg) {
				if(msg != null){
					layer.msg(msg.message);
					if(msg.flag){
						//掉用父窗体的方法
						parent.searchCustomer();
						cancel();
					}
				}
			}
		});
		
	}
	
	/**
	 * vip 积分赠送保存
	 * @returns
	 */
	function vipSave(){
		let url = prefix + "vipSave";
		if(!$('#vipHandleForm').form('validate')){
			layer.msg("请选择赠送类型!");
			return ;
		}
		let param = $("#vipHandleForm").serialize();
		$.ajax({
			url : url,
			type:"POST",
			data : param, // 
			async : false,
			success : function(msg) {
				if(msg != null){
					layer.msg(msg.message);
					if(msg.flag){
						//掉用父窗体的方法
						parent.searchCustomer();
						cancel();
					}
				}
			}
		});
		
	}
	/**
	 * 积分兑换保存
	 * @returns
	 */
	function subSave(){
		let url = prefix + "subSave";
		if(!$('#vipHandleForm').form('validate')){
			layer.msg("请选择赠送类型!");
			return ;
		}
		let param = $("#vipHandleForm").serialize();
		$.ajax({
			url : url,
			type:"POST",
			data : param, // 
			async : false,
			success : function(msg) {
				if(msg != null){
					layer.msg(msg.message);
					if(msg.flag){
						//掉用父窗体的方法
						parent.searchCustomer();
						cancel();
					}
				}
			}
		});
		
	}
	
	function cancel(){
		var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
		parent.layer.close(index);
	}