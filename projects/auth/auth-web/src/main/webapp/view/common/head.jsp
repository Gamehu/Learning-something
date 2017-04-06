<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>head</title>
</head>
<body>
	<span class="fl_r oper pos_r"> <a href="#" class="user">▼</a> <span
		class="pos_a set_up"> <a href="#">升级说明</a><a href="#">个人设置</a><a
			href="#">退出登录</a>
	</span> <a href="#" class="nav_add"><img src="../../img/add.png" alt="" /></a>
		<a href="#" class="nav_bell"><img src="../../img/bell.png" alt="" /></a>
		<a href="#" class="nav_list"><img src="../../img/list.png" alt="" /></a>
	</span>
	<a href="#" class="fl_l logo"><img src="../../img/logo.png" alt="" /></a>
	<span class="fl_l nav"> <a href="#">主页</a> <a href="#"
		class="nav_chose">成交操作</a> <a href="#">客户</a> <a href="#">财务结算</a>
		<a href="#">管理</a>
	</span>

	<script type="text/javascript">
		$(function() {
			$(".nav_add").click(function() {
				$(".nav_add_list").toggle(100);
			});
		    $(".user").click(function() {
		        $(".set_up").toggle(100);
		    });
		})
	</script>
	<div class="col-md-1 nav_add_list"
		style="position: absolute; padding-top: 64px; right: 20%;">
		<ul class="list-group">
			<li class="list-group-item"><i class="glyphicon-remove-sign"></i></li>
			<li class="list-group-item">配货合同</li>
			<li class="list-group-item">租船合同</li>
			<li class="list-group-item">付款</li>
		</ul>
	</div>
</body>
</html>