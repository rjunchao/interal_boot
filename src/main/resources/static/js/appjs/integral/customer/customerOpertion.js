	var prefix = "/integral/customer/";
	/**
	 * 
	 * @param type
	 * @returns
	 */
	function save(type){
		let url = prefix + "addSave";
		if( 2 == type){
			url = prefix + "editSave/" + hiddenFlag;//修改保存
		}
		if(!$('#customerForm').form('validate')){
			$.messager.alert('提示','红色框必填!');
			return ;
		}
		
		let param = $("#customerForm").serialize();
		/*let tempArr = param.split("&");
		let vo = {};
		for(let i = 0, len = tempArr.length; i < len; i++){
			let t = tempArr[i].split("=");
			vo.t[0] = t[1];
		}*/
		$.ajax({
			cache : false,
			type : "POST",
			url : url,
			data : param, // 
			async : false,
			error : function(request) {
				alert("Connection error");
			},
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
	 * 取消，关闭弹窗
	 * @returns
	 */
	function cancel(){
		var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
		parent.layer.close(index);
	}