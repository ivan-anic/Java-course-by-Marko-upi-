<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">
	<table border='1' style="width: 30%">
		<tr>
			<td>key</td>
			<td>sin</td>
			<td>cos</td>
		</tr>
		<c:forEach var="z" items="${mapSin}">
			<tr>
				<td>${z.key}</td>
				<td>${z.value[0]}</td>
				<td>${z.value[1]}</td>
			</tr>
		</c:forEach>

	</table>

	<p>
		<a href="index.jsp">Back to home</a>
	</p>


</body>
</html>