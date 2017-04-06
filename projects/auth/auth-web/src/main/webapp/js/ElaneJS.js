/**
 * 定义全局对象
 * 依赖：jquery、bootstrap、select2
 */
var ElaneJS = $.extend({}, ElaneJS);
$.ajaxSetup ({ 
	cache: false //close AJAX cache 
	});

/**
 * bootstrap select2
 */
ElaneJS.select2 = function(id, url) {
	$("#" + id).select2({
		placeholder : {
			id : "-1",
			text : "请输入检索内容"
		},
		minimumInputLength : 1,
		allowClear : true,
		escapeMarkup : function(markup) {
			return markup;
		},
		ajax : {
			url : url,
			type : "post",
			dataType : "json",
			data : function(params) {
				var query = {
					term : params.term
				};
				return query;
			},
			processResults : function(data, params) {
				return {
					results : data
				};
			},
			cache : true
		}
	});
}

/**
 * bootstrap select2
 */
ElaneJS.select2AllData = function(id, url) {
	$.ajax({
		url : url,
		async : false,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data != null) {

				$("#" + id).select2({
					placeholder : {
						id : "-1",
						text : "请输入检索内容"
					},
					allowClear : true,
					data : data
				});
			}
		}
	});
}
/**
 * 错误提示
 */
ElaneJS.popupError = function(content) {
	$("#popupErrorContent").html(content);
	$("#popupError").modal();
}

/**
 * 成功提示
 */
ElaneJS.popupSuccess = function(content) {
	$("#popupSuccessContent").html(content);
	$("#popupSuccess").modal();
}

/**
 * 提示信息
 */
ElaneJS.popupAlert = function(content) {
	$("#popupAlertContent").html(content);
	$("#popupAlert").modal();
}
/**
 * 菜单页面跳转
 */
ElaneJS.load = function(divid,url) {
	var timestamp = parseInt(new Date().getTime()/1000);
    if (typeof divid === 'string' && typeof url === 'string'){
        if (typeof $("#"+divid) === 'object') {
        	$("#"+divid).load(url,{"timestamp":timestamp});
        }
    }
}