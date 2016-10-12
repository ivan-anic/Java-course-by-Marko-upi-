<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">
	<p>Invalid arguments! Valid arguments are:</p>
	<ul>
		<li>a - (integer from [-100,100]</li>
		<li>b - (integer from [-100,100]</li>
		<li>n - (integer from [1,5]</li>
	</ul>

	<p>
		<a href="index.jsp">Back to home</a>
	</p>

</body>
</html>