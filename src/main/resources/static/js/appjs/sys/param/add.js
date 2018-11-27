//var menuTree;

var menuIds;
$(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	var param = $('#paramForm').serialize();
	$.ajax({
		cache : false,
		type : "POST",
		url : "/system/param/save",
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
					parent.reloadParam();
					var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
					parent.layer.close(index);
				}
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#paramForm").validate({
		rules : {
			paramCode : {
				required : true
			},
			paramValue: {
				required : true
			}
		},
		messages : {
			paramCode : {
				required : icon + "请输入唯一参数编码"
			},
			paramValue: {
				required : icon + "请输入参数值"
			}
		}
	});
}