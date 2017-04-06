/**
 * demo
 */

// your custom ajax request here

/*******************************************************************************
 * 查询-按钮事件（刷新列表，根据检索条件查询）
 */
function query() {
	$('#table').bootstrapTable("refresh");
}
/*******************************************************************************
 * 封装页面查询条件
 * 
 * @param params
 * @returns
 */
function packageQueryParams(params) {
	var queryParams = {
		limit : params.limit, // 每页显示条数
		offset : params.offset, // 偏移量
		docDate : $("#docDate").val(),// 创建日期
	};
	return queryParams;
}
function operateFormatter(value, row, index) {
    return [
                        '<a class="edit glyphicon glyphicon-edit" style="margin-left:10px" href="javascript:void(0)" title="编辑">',
                        '</a>',
                        '<a class="remove glyphicon glyphicon-trash" style="margin-left:10px" href="javascript:void(0)" title="移除">',
                        '</a>'
                    ].join('');
}
/***
 * 时间坚挺
 */
window.operateEvents = {
    'click .like': function (e, value, row, index) {
        ElaneJS.popupAlert(row.id);
    },
    'click .edit': function (e, value, row, index) {
        ElaneJS.popupAlert(row.id+"edit");
    },
    'click .remove': function (e, value, row, index) {
        ElaneJS.popupAlert(row.id+"remove");
    }
};
/*******************************************************************************
 * 初始化表格
 * 
 * @returns
 */
function initTable() {
	$('#table').bootstrapTable({
		// url: ctx + '/om/saleorder/query', //请求后台的URL（*）
		// method: 'post', //请求方式（*）
		// contentType: 'application/x-www-form-urlencoded',
		data : data01,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                  //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams: packageQueryParams,//传递参数（*）
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        columns: [
                  {field: 'demofield1',title: '合同编号',
                	  formatter:function(value,  row, index){return '<a target="_blank" href="#'+row.demofield1+'">' + row.demofield1 + '</a>';},
                	  align:"center"},
                  {field: 'demofield2', title: '出租人',align:"center",dataalign:"left"},
                  {field: 'demofield3', title: '货物',align:"center"},
                  {field: 'demofield4', title: '受载期',align:"center"},
                  {field: 'demofield4', title: '运费率',align:"center"},
                  {field: 'demofield4', title: '合同日期',align:"center"},
                  {field: 'demofield4', title: '船舶航次',align:"center"},
                  {field: 'operate', title: '操作',align:"center",formatter: operateFormatter,events: operateEvents}
                 ],
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
        	// 加载行项目
        	expandDetailView(index, row, $detail);
        }
	});
	$('#table2').bootstrapTable({
		// url: ctx + '/om/saleorder/query', //请求后台的URL（*）
		// method: 'post', //请求方式（*）
		// contentType: 'application/x-www-form-urlencoded',
		data : data01,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                  //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams: packageQueryParams,//传递参数（*）
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        //注册加载子表的事件。注意下这里的三个参数！
        onExpandRow: function (index, row, $detail) {
        	// 加载行项目
        	expandDetailView(index, row, $detail);
        }
	});
}
$(function() {
	$('#button').click(function() {
		ElaneJS.popupAlert("popupErrorContent");
	});
	$('#but_query').click(function() {
		query();
	});

	$('.date_btn').click(function() {
		$(this).parent().find('input').click();
	});

	$('.form_datetime').daterangepicker({
		"rangesType" : "auto",
		"startDate" : moment().startOf('month'),
		"endDate" : moment().endOf('month')
	});
	
	initTable();
});