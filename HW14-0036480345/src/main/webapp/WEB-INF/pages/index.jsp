<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<link href="<c:url value="css/style.css" />" rel="stylesheet">
</head>
<body>
	<h1>Welcomme to the poll app!</h1>
	<p>Please pick the poll you want to enter:</p>
	<ol>
		<c:forEach var="z" items="${listPolls}" varStatus="loop">
			<li><a href="votingIndex?pollID=<c:out value="${z.id}"></c:out>">${z.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>