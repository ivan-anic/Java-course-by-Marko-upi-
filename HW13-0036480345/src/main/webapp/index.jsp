<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">

	<h2>
		<a href="colors.jsp">Background color chooser</a>
	</h2>
	<h2>
		<a href="trigonometry?a=0&b=90">Trigonometric function obtainer</a>
	</h2>
	<p>gets the sin(x) and cos(x) functions for the given parameter in
		degrees.</p>
	<h2>
		<a href="funny.jsp">A funny story</a>
	</h2>
	<p>Displays a randomly colored funny story</p>
	<h2>
		<a href="reportImage.jsp">OS usage diagram</a>
	</h2>
	<p>shows a diagram of OS usage</p>
	<h2>
		<a href="powers?a=1&b=100&n=3">Powers .xls</a>
	</h2>
	<p>creates an .xls file with powers of numbers from a to b</p>
	<h2>
		<a href="appinfo.jsp">how long is the webapp running?</a>
	</h2>
	<p>displays the amount of time this application has been running</p>

	<h2>
		<a href="votingIndex">interactive voting application</a>
	</h2>
	<p>Runs the interactive voting!</p>

</body>
</html>