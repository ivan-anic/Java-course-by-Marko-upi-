<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">
	<h1>Favourite band poll:</h1>
	<p>Among the following bands, which one is your favourite?</p>
	<ol>
	<c:forEach var="z" items="${listResults}" varStatus="loop">
		<li><a href="voting-vote?id=${loop.count}">${z}</a></li>
	</c:forEach>
	</ol>

</body>
</html>