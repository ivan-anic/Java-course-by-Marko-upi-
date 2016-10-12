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
	<h1>Registration</h1>
	<p>Input your data:</p>

	<form action="register" method="post">
		
		First Name <input type="text" name="firstName" value='<c:out value="${entry.firstName}"/>' size="30"><br>
		<c:if test="${entry.imaPogresku('firstName')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('firstName')}"/></div>
		</c:if>

		Last Name <input type="text" name="lastName" value='<c:out value="${entry.lastName}"/>' size="50"><br>
		<c:if test="${entry.imaPogresku('lastName')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('lastName')}"/></div>
		</c:if>

		EMail <input type="text" name="email" value='<c:out value="${entry.email}"/>' size="100"><br>
		<c:if test="${entry.imaPogresku('email')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('email')}"/></div>
		</c:if>

		Nickname <input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="5"><br>
		<c:if test="${entry.imaPogresku('nick')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('nick')}"/></div>
		</c:if>		
		
		Password <input type="password" name="password" value='<c:out value="${entry.password}"/>' size="5"><br>
		<c:if test="${entry.imaPogresku('password')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('password')}"/></div>
		</c:if>
		
		<input type="submit" name="metoda" value="Pohrani">
		<input type="submit" name="metoda" value="Odustani">
		
		</form>
	
</body>
</html>