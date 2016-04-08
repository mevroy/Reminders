<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navbar navbar-fixed-top" role="navigation">
	<div class="navbar-inner">
		<div class="container">
			<!-- a class="brand active" href="${pageContext.request.contextPath}/${sessionScope.groupCode}/"><c:out value="${sessionScope.groupCode}"></c:out></a>  -->
			<a class="brand active" href="#"> <c:choose>
					<c:when test="${sessionScope.groupName ne null }">
						<c:out value="${sessionScope.groupName}"></c:out>
					</c:when>
					<c:otherwise>
			Portal
			</c:otherwise>
				</c:choose>
			</a>

			<c:if test="${sessionScope.groupMember ne null }">
				<ul class="nav pull-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Welcome, <c:choose><c:when test="${!empty sessionScope.groupMember.aliasName}">${sessionScope.groupMember.aliasName}</c:when><c:otherwise>${sessionScope.groupMember.firstName} ${sessionScope.groupMember.lastName}</c:otherwise></c:choose>
					</a></li>
				</ul>
			</c:if>
		</div>
	</div>
</div>
