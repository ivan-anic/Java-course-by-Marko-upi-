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
	<h1>New blog entry</h1>
	<p>Input your data:</p>

	<form action="new" method="post" >
		
		<input type="hidden" name="id" value='<c:out value="${entry.id}"></c:out>'>
	
		Title <input type="text" name="title" value='<c:out value="${entry.title}"/>' size="30"><br>
		<c:if test="${entry.imaPogresku('title')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('title')}"/></div>
		</c:if>

		Text <input type="text" name="text" value='<c:out value="${entry.text}"/>' size="50"><br>
		<c:if test="${entry.imaPogresku('text')}">
		<div class="greska"><c:out value="${entry.dohvatiPogresku('text')}"/></div>
		</c:if>
		
		<input type="submit" name="metoda" value="Pohrani">
		<input type="submit" name="metoda" value="Odustani">
		
		</form>
	
</body>
</html>