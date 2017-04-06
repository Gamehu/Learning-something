<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/view/common/popup.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>demo</title>
<style type="text/css">
.nav_add_list {
	line-height: 10px;
	display: none;
	postion: absolute;
}
</style>

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

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="../../plugin/html5shiv/html5shiv.js"></script>
      <script src="../../plugin/respond.js/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<!-- URL 弹出层 -->
	<div id="popupURL" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document" style="width: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="box-body"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="top" id="top">
		<!--头部导航条-->
		<jsp:include page="/view/common/head.jsp" />
	</div>
	<div class="clear"></div>
	<div id="mainframe" class="main">
		<div id="leftframe" class="left_list fl_l mt_15 bg_fff bd_grey">
			<!--中部左侧菜单-->
			<jsp:include page="/view/common/left.jsp" />
		</div>
		<div id="rightframe" class="right_info fl_l ml_15 mt_15 bg_fff bd_grey">
			<!--中部右侧内容-->
			
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>