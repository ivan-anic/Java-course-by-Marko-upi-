<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<link href="<c:url value="css/style.css" />" rel="stylesheet">
</head>
<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
	<c:forEach var="z" items="${listPollOptions}" varStatus="loop">
		<li><a href="voting-vote?id=<c:out value="${z.id}"/>">${z.optionTitle}</a></li>
	</c:forEach>
	</ol>
</body>
</html>