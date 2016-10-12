<%@ page
	import="hr.fer.zemris.java.hw15.model.BlogEntry, java.util.List, hr.fer.zemris.java.hw15.model.BlogUser, hr.fer.zemris.java.hw15.forms.BlogEntryForm, hr.fer.zemris.java.hw15.dao.DAOProvider"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<body>

	<p>blog author ${blogAuthor.firstName}</p>
	<c:choose>
		<c:when test="${entries==null}">
      Nema unosa!
    </c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="e" items="${entries}">
					<li><a href="/blog/servleti/author/${author}/${e.id}">${e.title}</a></li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

	<%if ((session.getAttribute("current.user.id")) instanceof Long && 
			(session.getAttribute("current.user.nick")).equals(request.getAttribute("author"))){%>
	<a class="button" href="/blog/servleti/author/${author}/new">Add a
		new entry</a> <%} %>
	<p>
		<a href="/blog/servleti/main">Back to home</a>
	</p>

</body>
</html>