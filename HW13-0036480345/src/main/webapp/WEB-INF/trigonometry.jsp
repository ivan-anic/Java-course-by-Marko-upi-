<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="true"%>

<html>
<body bgcolor="<%=session.getAttribute("pickedBgCol")%>">

	<table border='1' style="width: 100%">
		<tr>
			<td>8</td>
		</tr>
		<tr>
			<td>${a}</td>
		</tr>
		<tr>
			<td>${mapSin.size()}</td>
		</tr>
		<c:forEach var="z" items="${mapSin}">
			<tr>
				<td>${z.key},${z.value}</td>
			</tr>
		</c:forEach>

	</table>
	
		<ol>
			<c:forEach var="e" items="${primjerci}">
				<li>${e.key}==&gt;${e.value}, ${e.value}</li>
			</c:forEach>
		</ol>
	
</body>
</html>