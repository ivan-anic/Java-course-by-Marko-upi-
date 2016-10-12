<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<%
	Random r = new Random();
	String color = String.valueOf(Integer.toHexString(r.nextInt())).substring(0, 6);

	String colorbg = (String) session.getAttribute("pickedBgCol");
	if (colorbg == null)
		colorbg = "white";
%>


<html>
<body bgcolor="<%=colorbg%>">
	<p style="color:<%=color%>;font-family:Comic Sans MS">Here come
		dat boi.</p>

	<p style="color:<%=color%>;font-family:Comic Sans MS">Wadup.
	</p>

	<p>
		<a href="index.jsp">Back to home</a>
	</p>

</body>
</html>