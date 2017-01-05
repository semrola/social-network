<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>vnosObvestil</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="unlogged.jsp" />
	<jsp:include page="logged_admin.jsp" />
	
	<p class="error">
		<%
			if(request.getAttribute("sms")!=null)
				out.print(request.getAttribute("sms"));
		 %>
	</p>
	
	<form method="post" action="PosiljanjeObvestilaHandlerServlet">
	<table>
		<tr><td>Naslov obvestila</td><td><input type="text" name="naziv" /></td></tr>
		<tr><td>Vsebina obvestila</td><td></td></tr>
		<tr><td colspan="2"><textarea name="vsebina" rows="7" cols="50"></textarea></td></tr>
		<tr><td><input type="submit" value="Pošlji obvestilo" /></td></tr>
	</table>
	</form>
</body>
</html>