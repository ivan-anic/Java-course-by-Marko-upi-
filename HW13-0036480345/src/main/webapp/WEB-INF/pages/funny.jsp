<%@ page import="java.util.Date,java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<% 	
Random r = new Random();
String color = String.valueOf(Double.toHexString(r.nextDouble()*(4294967295.0)));
%>

<html>
<body>
	<p style="color:<%= color %>;">
	neka prichica
	<%= color %>
	</p>
	
</body>
</html>