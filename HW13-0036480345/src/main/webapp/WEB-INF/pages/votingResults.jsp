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
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor="<%=color%>">
	<h1>Voting results:</h1>
	<p>These are the voting results:</p>
	<table border='1' style="width: 30%">
		<tr>
			<td>Band name</td>
			<td>Results</td>
		</tr>
		<c:forEach var="z" items="${mapResults}">
			<tr>
				<td>${z.key}</td>
				<td>${z.value}</td>
			</tr>
		</c:forEach>
	</table>
	<h1>Graphical results:</h1>
	<img
		src="reportImage?
		<c:forEach var="z" items="${mapResults}">
			${z.key}=${z.value}&
		</c:forEach>	
	">
	<h1>.xls results:</h1>
	<p>
		Click <a href="voting-xls">here</a> to download!
	</p>

	<h1>Popular songs(according to results):</h1>
	<ul>
		<c:forEach var="y" items="${mapWinners}">
			<li><a href="${y.value}" target="_blank">${y.key}</a></li>
		</c:forEach>
	</ul>
	<p>
		<a href="index.jsp">Back to home</a>
	</p>
</body>
</html>