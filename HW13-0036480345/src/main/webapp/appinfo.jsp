<%@ page session="true"%>

<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null)
		color = "white";
%>

<html>
<body bgcolor="<%=color%>">

	<p>
		<%
			long now = System.currentTimeMillis();
			long then = (Long) getServletContext().getAttribute("time");
			long diff = now - then;

			int days = (int) diff / 86400000;
			int hours = (int) diff % 86400000 / 3600000;
			int minutes = (int) diff % 86400000 % 3600000 / 60000;
			int seconds = (int) diff % 86400000 % 3600000 % 60000 / 1000;
			int milliseconds = (int) diff % 86400000 % 3600000 % 60000 % 1000;

			out.write("The webapp has been running for: " + days +
					" days, " + hours + " hours, " + minutes + " minutes, " +
					seconds + " seconds, and " + milliseconds + " milliseconds.");
		%>
	</p>

	<p>
		<a href="index.jsp">Back to home</a>
	</p>


</body>
</html>