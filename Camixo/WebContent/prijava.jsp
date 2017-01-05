<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Prijava</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="document.getElementById('user').focus()">
	<%
	String sms=String.valueOf(request.getAttribute("sms")); //sporocilo iz handlerja
	%>
	
	<h1>Prijava</h1>
	<p>
		
		<p class="error">
		<% 
		if(request.getAttribute("sms")!=null)
			out.print(sms);
		%>
		
		
		<form method="post" action="PrijavaHandlerServlet">
		<table>
			<tr><td>Uporabniško ime:</td><td><input type="text" name="uime" id="user" /></td></tr>
			<tr><td>Geslo:</td><td><input type="password" name="phrase" /></td></tr>
			<tr><td></td><td><input type="submit" value="Prijava" /></td></tr>
		</table>
		</form>
		
	<%
	if(request.getAttribute("odjava")!=null)
	{
		request.removeAttribute("odjava");
		%>
		<jsp:forward page="index.jsp" />
		<%
	}	
	%>
</body>
</html>