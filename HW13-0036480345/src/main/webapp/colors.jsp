<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">
	<h2>Choose your color!</h2>
	<p>
		<a href="setcolor?pickedBgCol=red">Red</a>
	</p>
	<p>
		<a href="setcolor?pickedBgCol=white">White</a>
	</p>
	<p>
		<a href="setcolor?pickedBgCol=green">Green</a>
	</p>
	<p>
		<a href="setcolor?pickedBgCol=cyan">Cyan</a>
	</p>

	<p>
		<a href="index.jsp">Back to home</a>
	</p>

</body>
</html>
