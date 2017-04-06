<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<o:header title="User"/>
<o:topbar pageName="User"/>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span10 offset1">
		    <h1 class="page-header">日志查询</h1>
		    <div class="tabbable">
		        <ul class="nav nav-tabs">
		            <li class="active"><a href="#tab1" data-toggle="tab">基础查询</a>
		            </li>
		            <li><a href="#tab2" data-toggle="tab">事务查询</a>
		            </li>
		        </ul>
		        <!-- 选项卡相对应的内容 -->
		        <div class="tab-content">
		            <div class="tab-pane active" id="tab1">
						<form id="logParameter1">
						<table class="table" id="role_table">
							<thead><td width="30%">key</td><td width="70%">value</td></thead>
							<tbody id="role_table_tbody">
								<security:authorize access="hasRole('ROLE_ADMIN')">
									<tr><td>app_id</td><td><input id="app_id" name="app_id" value="test"/></td></tr>
								</security:authorize>
								<tr><td>company_id</td><td><input id="company_id" name="company_id"/></td></tr>
								<tr><td>user_id</td><td><input id="user_id" name="user_id"/></td></tr>
								<tr><td>database</td><td><input id="database" name="database"/></td></tr>
								<tr><td>table</td><td><input id="table" name="table"/></td></tr>
								<tr><td>tsBegin</td><td><input id="tsBegin" name="tsBegin"/></td></tr>
								<tr><td>tsEnd</td><td><input id="tsEnd" name="tsEnd"/></td></tr>
								<tr><td>pageNo</td><td><input id="pageNo" name="pageNo" value="1"/></td></tr>
							</tbody>
						</table>
						</form>
						<input type="submit" class="btn btn-large" style="float: right;" value="查询" onclick="ajaxLog(1);">
		            </div>
		            <div class="tab-pane" id="tab2">
						<form id="logParameter2">
						<table class="table" id="role_table">
							<thead><td width="30%">key</td><td width="70%">value</td></thead>
							<tbody id="role_table_tbody">
								<security:authorize access="hasRole('ROLE_ADMIN')">
									<tr><td>app_id</td><td><input id="app_id" name="app_id" value="test"/></td></tr>
								</security:authorize>
								<tr><td>xtransaction_id</td><td><input id="xtransaction_id" name="xtransaction_id"/></td></tr>
								<tr><td>tsBegin</td><td><input id="tsBegin" name="tsBegin"/></td></tr>
								<tr><td>tsEnd</td><td><input id="tsEnd" name="tsEnd"/></td></tr>
								<tr><td>pageNo</td><td><input id="pageNo" name="pageNo" value="1"/></td></tr>
							</tbody>
						</table>
						</form>
						<input type="submit" class="btn btn-large" style="float: right;" value="查询" onclick="ajaxLog(2);">
		            </div>
		        </div>
		    </div>
	    </div>
    </div>
</div>
<div class="container-fluid main">
	<div class="row-fluid">
		<div class="span10 offset1">
			
			<div>
				<table class="table">
					<thead><td>_id</td><td>database</td><td>table</td><td>type</td><td>ts</td>
					<td>app_id</td><td>company_id</td><td>user_id</td><td>xtransaction_id</td><td>操作</td></thead>
					<tbody id="logView">
						
					</tbody>
				</table>
			</div>
			<div id="modelContainer"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function () {
	});
	
	function ajaxLog(type){
		var url = "";
		switch(type){
		case 1:
			url = "logview/log";
		  break;
		case 2:
			url = "logview/transaction";
		  break;
		default:
		}
		
		$.ajax({
            //cache: true,
            //async: false,
            type: "POST",
            url: url,
            data:$('#logParameter'+type).serialize(),
            error: function(request, data) {
            	console.log(request);
            	console.log(data);
            },
            success: function(result) {
            	if(result.meta.status == 1){
            		data = result.data;
            	}else{
            		data = "";
            	}
            	$("#logView").text("");
            	//遍历集合
            	if(data.length > 0){
            		var result = "";
            		var modelContent = "";
	            	for(var o in data){  
	                    //alert(data[o]);
	                    result = result + 
	                    	"<tr>" +
	                    	"<td>"+ data[o]._id +"</td>" +
	                    	"<td>"+ data[o].database +"</td>" +
	                    	"<td>"+ data[o].table +"</td>"+
	                    	"<td>"+ data[o].type +"</td>"+
	                    	"<td>"+ data[o].ts +"</td>"+
	    					"<td>"+ data[o].app_id +"</td>"+
	    					"<td>"+ data[o].company_id +"</td>"+
	    					"<td>"+ data[o].user_id +"</td>"+
	    					"<td>"+ data[o].xtransaction_id +"</td>"+
	    					"<td style='color:#0088cc' data-toggle='modal' data-target='#"+ data[o]._id +"'>展开</td>"+
	    					"</tr>";
    					modelContent = modelContent + 
    						"<div class='modal hide' id='"+ data[o]._id +"' tabindex='-1' >"+ 
    						"<div class='modal-dialog modal-lg'><div class='modal-body'><textarea style='resize:none;width:500px;height:700px' >"+ formatJson(data[o]) +"</textarea></div>"+ 
	    					"</div></div>";
	                }
               		$("#logView").append(result);
               		
               		$("#modelContainer").text("");
               		$("#modelContainer").append(modelContent);
            	}else{
            		var result = "<tr><td colspan=11>没有查询到数据</td>";
            		$("#logView").append(result);
            	}
            	
            }
        });
	}
</script>

<o:footer />