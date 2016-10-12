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
		<li><a href="voting-vote?id=1">The Cranberries</a></li>
		<li><a href="voting-vote?id=2">The Peaches</a></li>
		<li><a href="voting-vote?id=3">Smashing Pumpkins</a></li>
		<li><a href="voting-vote?id=4">Vanilla Ice</a></li>
		<li><a href="voting-vote?id=5">Red Hot Chili Peppers</a></li>
		<li><a href="voting-vote?id=6">Black Eyed Peas</a></li>
		<li><a href="voting-vote?id=7">Vanilla Fudge</a></li>
	</ol>

</body>
</html>