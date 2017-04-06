<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title></title>
</head>
<body>
    <!-- jquery -->
    <script type="text/javascript" src="../../plugin/jquery/jquery-1.10.2.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="../../plugin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../plugin/bootstrap/css/bootstrap-theme.css">
    <script type="text/javascript" src="../../plugin/bootstrap/js/bootstrap-3.2.0.1.js"></script>

    <!-- Select2 -->
    <link rel="stylesheet" href="../../plugin/select2/select2.min.css">
    <script type="text/javascript" src="../../plugin/select2/select2.full.min.js"></script>
    <script type="text/javascript" src="../../plugin/select2/i18n/zh-CN.js"></script>

    <!-- Table表格 -->
    <link rel="stylesheet" href="../../plugin/bootstrap-table/dist/bootstrap-table.css">
    <script type="text/javascript" src="../../plugin/bootstrap-table/dist/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="../../plugin/bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js"></script>

    <!-- 时间控件 -->
    <link rel="stylesheet" type="text/css" media="all" href="../../plugin/bootstrap-daterangepicker/daterangepicker.css" />
    <script type="text/javascript" src="../../plugin/bootstrap-daterangepicker/moment.js"></script>
    <script type="text/javascript" src="../../plugin/bootstrap-daterangepicker/daterangepicker-2.1.24.1.js"></script>

    <!-- jquery.validate -->
    <script src="../../plugin/jquery-validation/jquery.validate.js" charset="utf-8"></script>
    <script src="../../plugin/jquery-validation/localization/messages_zh.js" charset="utf-8"></script>

    <!-- 自定义css -->
    <link rel="stylesheet" href="../../css/style.css" />
    <script type="text/javascript" src="../../js/ElaneJS.js"></script>
	<h2>配货合同</h2>
	<p class="pl_20 screen">
		<a href="#" class="fl_r mr_30 blue_btn mt_10">新建</a> <input type="text" name="" id="" placeholder="合同编号/出租人/货名/装卸港名" />
		<span class="date_box"> <input type="text" name="" id="" placeholder="受载期范围" class="form_datetime" /> <a
			href="#" class="date_btn"><img src="../../img/date.jpg" alt="" /></a>
		</span> <span class="date_box"> <input type="text" name="" id="" placeholder="合同日期范围" class="form_datetime" /> <a
			href="#" class="date_btn"><img src="../../img/date.jpg" alt="" /></a>
		</span> <a id="but_query" href="#" class="blue_btn">搜索</a>
	</p>
	<table id="table" data-reorderable-columns="true"></table>
	<table id="table2" data-reorderable-columns="true">
        <thead>
            <tr>
                <th data-field="demofield1" data-align="left" data-halign="right">合同编号</th>
                <th data-field="demofield2">出租人</th>
                <th data-field="demofield3">货物</th>
                <th data-field="demofield4">受载期</th>
                <th data-field="demofield4">运费率</th>
                <th data-field="demofield4">合同日期</th>
                <th data-field="demofield4">船舶航次</th>
                <th data-field="option" data-formatter="operateFormatter" data-events="operateEvents">操作</th>
            </tr>
        </thead>
    </table>
	
    <script type="text/javascript" src="../../js/demo/data01.js"></script>
    <script type="text/javascript" src="../../js/demo/demo.js"></script>
</body>
</html>