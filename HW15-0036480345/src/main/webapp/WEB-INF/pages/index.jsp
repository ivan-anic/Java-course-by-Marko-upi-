<%@ page import="java.util.Date,java.util.Random"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<html>

<head>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		}
		</style>
	</head>

<body>
<p align="right">

	<c:choose>
		<c:when test="<%= session.getAttribute(\"current.user.id\") instanceof Long %>">
			<p>
				<%= session.getAttribute("current.user.fn")%> <%= session.getAttribute("current.user.ln")%>,
				<a class="button" href="logout">Logout</a>
			</p>
		</c:when>
		<c:otherwise>
			<p>
				Anonymous user
			</p>
		</c:otherwise>
	</c:choose>
</p>
 
	<h1>Welcomme to the blog!</h1>

<c:if test="<%= !(session.getAttribute(\"current.user.id\") instanceof Long) %>">
	<form action="login" method="post">

		Nickname <input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="5"><br>
		<c:if test="${entry.imaPogresku('nick')}">
			<div class="greska"><c:out value="${entry.dohvatiPogresku('nick')}"/></div>
		</c:if>	

		Password <input type="password" name="password" size="5"><br>
		<c:if test="${entry.imaPogresku('password')}">
			<div class="greska"><c:out value="${entry.dohvatiPogresku('password')}"/></div>
		</c:if>

		<input type="submit" name="metoda" value="Prijava">
	</form>

	<a class="button" href="register">Registration</a>
</c:if>

	<p>${num}</p>
	<ol>
		<c:forEach var="z" items="${authors}" varStatus="loop">
			<li><a href="author/<c:out value="${z.nick}"></c:out>">${z.firstName} ${z.lastName}</a></li>
		</c:forEach>

	</ol>
</body>
</html>