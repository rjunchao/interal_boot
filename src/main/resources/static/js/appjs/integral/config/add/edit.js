	$(function(){
		validateIntegralConfig();
	});
	$.validator.setDefaults({
		submitHandler : function() {
			save();
		}
	});
	
	function save(){
		
		let param = $('#addIntegralConfigFrom').serialize();
		let url =  "/integral/config/updateSave";
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
						parent.reloadIntegralConfig();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);
					}
				}
			}
		});
	}
	function validateIntegralConfig(){
		let icon = "<i class='fa fa-times-circle'></i> ";
		let type = $("#integralType").val();
		$("#addIntegralConfigFrom").validate({
			rules : {
				integralTypeName : {
					required : true
				},
				integralCoefficient: {
					required : true
				}
			},
			messages : {
				integralTypeName : {
					required : icon + "请输入添加类型"
				},
				integralCoefficient: {
					required : icon + "请输入类型计算比例"
				}
			}
		});
	}
