
	$(function(){
		validateRule();
	});
	$.validator.setDefaults({
		submitHandler : function() {
			save();
		}
	});
	/**
	 * 保存
	 * @returns
	 */
	function save(){
		let json = $("#paramEditForm").serialize();//得到数据
		
		$.ajax({
			url:"/system/param/updateSave",
			cache:false,
			async:false,
			data:json,
			type:"POST",
			error:function(e){
				layer.msg("connect error");
			},
			success:function(msg){
				
				if(msg != null){
					layer.msg(msg.message);
					if(msg.flag){
						//掉用父窗体的方法
						parent.reloadParam();
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
						parent.layer.close(index);
					}
				}
			}
		});
	}
	/**
	 * 校验
	 * @returns
	 */
	function validateRule(){
		let icon = "<i class='fa fa-times-circle'></i> ";
		$("#paramEditForm").validate({
			rules : {
				paramValue: {
					required : true
				}
			},
			messages : {
				paramValue: {
					required : icon + "请输入参数值"
				}
			}
		});
	}