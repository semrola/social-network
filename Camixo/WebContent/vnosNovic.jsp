<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*, java.text.SimpleDateFormat, dbJPA.Uporabnik" %>
<html>
<head>
<title>vnosNovic</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<jsp:include page="unlogged.jsp" />
	<jsp:include page="logged_admin.jsp" />
	
	<%
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
			
		//Datum
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("d.M.y");
		String s=sdf.format(d);
	%>
	
	<p class="error">
		<%
			if(request.getAttribute("sms")!=null)
				for(String sms:(ArrayList<String>)request.getAttribute("sms"))
					out.print(sms+"<br />");
		 %>
	</p>
	<form method="post" action="DodajanjeNovicHandlerServlet">
	<table>
		<tr><td>Ime novice</td><td><input type="text" name="imeNovice" /></td></tr>
		<tr><td>Avtor</td><td><input type="text" name="avtor" value="<%=u.getUsername() %>" disabled /></td></tr>
		<tr><td>Datum</td><td><input type="text" value="<%=s %>" name="datum" disabled /></td></tr>
		<tr><td>Vsebina novice</td></tr>
		<tr><td colspan="2"><textarea name="vsebina" rows="7" cols="50"></textarea></td></tr>
		<tr><td><input type="submit" value="Dodaj novico" /></td></tr>
	</table>
	</form>
</body>
</html>