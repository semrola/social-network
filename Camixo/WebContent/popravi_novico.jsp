<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@page import="db.Novica, helper.*, dbJPA.Uporabnik" %>
<html>
<head>
<title>popravi_novico</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="unlogged.jsp" />
	<jsp:include page="logged_admin.jsp" />
	<%
		Novica n=(Novica)request.getAttribute("novica");
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
	%>
	
	<p class="error">
		<%
			if(request.getAttribute("sms")!=null)
				out.print(request.getAttribute("sms")+"<br />");
		 %>
	</p>
	<form method="post" action="UrejanjeNoviceHandlerServlet">
	<input type="hidden" name="id_novica" value="<%=n.getIdNovica()%>" />
	<table>
		<tr><td>Ime novice</td><td><input type="text" name="imeNovice" value="<%=n.getNaziv()%>"/></td></tr>
		<tr><td>Avtor</td><td><input type="text" name="avtor" value="<%=u.getUsername() %>" disabled /></td></tr>
		<tr><td>Datum</td><td><input type="text" value="<%=Helper.vrniLepoOblikoDatuma(n.getDatumObjave()) %>" name="datum" disabled /></td></tr>
		<tr><td>Vsebina novice</td></tr>
		<tr><td colspan="2"><textarea name="vsebina"  rows="7" cols="50"><%=n.getVsebina() %></textarea></td></tr>
		<tr><td><input type="submit" value="Uredi novico" /></td></tr>
	</table>
	</form>
</body>
</html>