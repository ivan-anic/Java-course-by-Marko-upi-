<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>
<link href="<c:url value="css/style.css" />" rel="stylesheet">
</head>
<body>
	<h1>Voting results:</h1>
	<p>These are the voting results:</p>
	<table border='1' style="width: 30%">
		<tr>
			<td>Item</td>
			<td>Results</td>
		</tr>
		<c:forEach var="z" items="${listResults}">
			<tr>
				<td>${z.optionTitle}</td>
				<td>${z.votesCount}</td>
			</tr>
		</c:forEach>
	</table>
	<h1>Graphical results:</h1>
	<img src="reportImage">
	<h1>.xls results:</h1>
	<p>
		Click <a href="voting-xls">here</a> to download!
	</p>
	<h1>Popular items(according to results):</h1>
	<ul>
		<c:forEach var="y" items="${listWinners}">
			<li><a href="${y.optionLink}" target="_blank">${y.optionTitle}</a></li>
		</c:forEach>
	</ul>
	<p>
		<a href="index.html">Back to home</a>
	</p>
</body>
</html>