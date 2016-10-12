<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>
<body>
	<%if ((session.getAttribute("current.user.id")) instanceof Long && 
			(session.getAttribute("current.user.nick")).equals(request.getAttribute("author"))) { %>
	<a class="button" href="/blog/servleti/author/
	<% out.print(session.getAttribute("current.user.nick")); %>
	/edit/${entry.id}">Edit this entry</a>
	<%} %>
	
	<p>Title:</p>
	<div style="font-weight: bold">
		<c:out value="${entry.title}" />
	</div>
	<p>Message</p>
	<div style="font-weight: bold">
		<c:out value="${entry.text}" />
	</div>

	<c:choose>
		<c:when test="${empty blogentry.comments}">
			<p>No Comments so far.</p>
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="e" items="${blogentry.comments}">
					<li><div style="font-weight: bold">
							User:
							<c:out value="${e.usersEail}" />
							Posted on:
							<c:out value="${e.postedOn}" />
						</div>
					<li><div>
							Message=
							<c:out value="${e.message}" />
						</div>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

	<form action="postComment" method="post">
		<input type="hidden" name="id" value='<c:out value="${entry.id}"></c:out>'>
	
		<textarea name="comment" rows="5" cols="30"></textarea>
		<br><input type="Submit" name="method-comment" value="Submit">
	</form>

	<p>
		<a class="button" href="/blog/servleti/author/${author}/new"> Post
			a new comment</a>
	</p>

	<p>
		<a href="/blog/servleti/main">Back to home</a>
	</p>

</body>
</html>