<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title><c:if test="${groupName ne null}"><c:out value="${groupName}"/> - </c:if><tiles:insertAttribute name="title" ignore="true"/></title>  
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/res/custom/images/email/generic/${sessionScope.groupCode}Logo.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/bootstrap/css/${sessionScope.cssSelector}united.bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/bootstrap/css/datepicker.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/bootstrap/css/datetimepicker.css"/>
        
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/jqGrid/css/ui.jqgrid.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/css/jquery-ui.structure.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/css/jquery-ui.theme.min.css"/>
    <!-- link rel="stylesheet" href="${pageContext.request.contextPath}/res/jqGrid/plugins/searchFilter.css"/>  -->
    
    <!-- link rel="stylesheet" href="${pageContext.request.contextPath}/res/bootstrap/css/bootstrap.css"/>-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/app.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/sticky.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/bootstrap/js/datepicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/bootstrap/js/datetimepicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery-ui.min.js"></script>
        
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/additional-methods.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery.validate.bootstrapfix.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/notify/notify.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery.countdown.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/res/jqGrid/js/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/jqGrid/js/grid.locale-en.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/custom/js/custom.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/js/css/jquery.ui.combify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery/jquery.ui.combify.js"></script> 
    <!-- script type="text/javascript" src="${pageContext.request.contextPath}/res/jqGrid/plugins/jquery.searchFilter.js"></script>  -->
  
    
    
    
    
</head>
<body>
<!-- page -->
<div class="wrapper">
    <div class="container">
        <!-- header  -->
        <header id="header">
            <tiles:insertAttribute name="header"/>
        </header> 
        <!-- /header -->

        <!-- message -->
        <div id="message">
            <tiles:insertAttribute name="message"/>
        </div>
        <!-- /header -->

        <!-- content -->
        <div id="content">
            <tiles:insertAttribute name="content"/>
        </div>
        <!-- /content -->
    </div>
    <div class="push"><!--//--></div>
</div>
<!-- /page -->
<!-- footer -->
<footer>
    <div class="container">
        <tiles:insertAttribute name="footer"/>
    </div>
</footer>
<!-- /footer -->
</body>
</html>
