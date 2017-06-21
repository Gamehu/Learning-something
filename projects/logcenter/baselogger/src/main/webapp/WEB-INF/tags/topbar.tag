<%@attribute name="pageName" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="o" tagdir="/WEB-INF/tags"%>
<c:choose>
	<c:when test="${ not empty userInfo }">
		<c:set var="longName" value="${ userInfo.nickname }" />
	</c:when>
</c:choose>
<div class="navbar navbar-inverse">
	<div class="navbar-inner">
		<div class="container">
			<button class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="brand">Baselogger</a>
			<c:if test="${ not empty pageName }">
				<div class="nav-collapse collapse">
					<ul class="nav">
						<c:choose>
							<c:when test="${pageName == 'Home'}">
								<li class="active"><a >Home</a></li>
							</c:when>
							<c:otherwise>
								<li><a href=".">Home</a></li>
							</c:otherwise>
						</c:choose>
						<security:authorize access="hasRole('ROLE_USER')">
							<c:choose>
								<c:when test="${pageName == 'User'}">
									<li class="active"><a >User</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="user">User</a></li>
								</c:otherwise>
							</c:choose>
						</security:authorize>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<c:choose>
								<c:when test="${pageName == 'Admin'}">
									<li class="active"><a >Admin</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="admin">Admin</a></li>
								</c:otherwise>
							</c:choose>
						</security:authorize>
					</ul>
					<ul class="nav pull-right">
	                    <security:authorize access="hasRole('ROLE_USER')">
						<li class="dropdown">
							<a id="userButton" class="dropdown-toggle" data-toggle="dropdown" href=""><i class="icon-user icon-white"></i> ${ longName } <span class="caret"></span></a>
							<ul class="dropdown-menu pull-right">
								<li><a href="user" data-toggle="collapse" data-target=".nav-collapse">${ longName }</a></li>
								<li class="divider"></li>
								<li><a href="j_spring_security_logout" data-toggle="collapse" data-target=".nav-collapse"><i class="icon-remove"></i> Log out</a></li>
							</ul>
						</li>
	                    </security:authorize>
	                    <security:authorize access="!hasRole('ROLE_USER')">
	                    <li>
	                    	<a id="loginButton" href="openid_connect_login" data-toggle="collapse" data-target=".nav-collapse"><i class="icon-lock icon-white"></i> Log in</a>
	                    </li>
	                    </security:authorize>
	                </ul>

	            </div><!--/.nav-collapse -->
			</c:if>
        </div>
    </div>
</div>