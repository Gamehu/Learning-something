<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<o:header title="Admin"/>
<o:topbar pageName="Admin"/>
<div class="container-fluid main">
	<div class="row-fluid">
		<div class="span10 offset1">

			<h1>角色管理</h1>
			<div>
				<p style="color:red">角色定义标准：role_xxx_admin，xxx使用app_id。<a href="oauth/userlist" target="_blank">查看用户中心现有角色</a></p>
			</div>
			<div>
				<input type="submit" class="btn" style="float: right;" value="删除" onclick="deleteRole();">
				<form action="role/save" id="roleCreate" method="post">
				<span style="font-size: 22px;">角色：</span><input type="text" name="role" >
				<span style="font-size: 22px;">备注：</span><input type="text" name="remark" >
				<input type="submit" class="btn" value="新建" style="margin-bottom: 10px;">
				</form>
			</div>
			<form action="role/delete" id="roleDelete" method="post">
			<table class="table" id="role_table">
				<thead><td>角色</td><td>备注</td><td>操作</td></thead>
				<tbody>
					<c:forEach items="${roles}" var="role">
						<tr><td>${role.role}</td><td>${role.remark}</td><td><input class="handleRole" name="role" type="checkbox" value="${role.role}" ></td></tr>
					</c:forEach>
				</tbody>
			</table>
			</form>
			<div>
				
			</div>
			<div>
			</div>

		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function () {
		
		
	});
	
	function deleteRole(){
		var count = 0;
		$(".handleRole").each(function(){
			if($(this).is(':checked')){
				//roles = roles + $(this).val();
				count = count + 1;
			}
		});
		if(count == 0){
			alert("请选中要删除的角色");
		}else{
	 		var r=confirm("确定要删除选中的角色？")
		  	if (r==true){
		    	$("#roleDelete").submit();
		    } else {
		    }
	  	}
	}
</script>

<o:footer />
