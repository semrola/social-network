<!DOCTYPE HTML><%@page import="dbJPA.Prijateljstvo"%>
<%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik, java.util.List" %> 
<html>
<head>
<title>Iskanje</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h1>Iskanje osebe</h1>
	<form action="IskanjeHandlerServlet" method="post">
	<table>
		<tr><td>Vnesite iskalni niz</td><td><input type="text" name="search" /></td></tr>
		<tr><td></td><td><input type="submit" value="Išči!" /></td></tr>
	</table>
	</form>
	
	<p class="error">
	<%
		if(request.getAttribute("sms")!=null)
			out.print(request.getAttribute("sms"));
		if(request.getAttribute("sms_prosnja")!=null)
			out.print(request.getAttribute("sms_prosnja"));
	%>
	</p>
	<table id="table-2">
	
	<%
		if(request.getAttribute("result")!=null)
		{
		%><thead><th>Št.</th><th>Uporabniško ime</th><th>Ime</th><th>Priimek</th><th>Status</th></thead><%
			Uporabnik up=(Uporabnik)session.getAttribute("prijava");
			int[] p=(int[])request.getAttribute("statusi");
			int i=1;
			boolean self=false;
			for(Uporabnik u:(List<Uporabnik>)request.getAttribute("result"))
			{
				
				%>
				<tr><td><%=i %></td><td><%=u.getUsername() %></td><td><%=u.getIme() %></td><td><%=u.getPriimek() %></td>
				
				<%if(up.getIdUporabnik()==u.getIdUporabnik()) { %>
				<td>/</td>
				<%self=true; } %>
				
				<%if(p[i-1]==10 && !self){ %>
				<td><a href="PosiljanjeZahteveZaPrijateljstvoInitServlet?idPrejemnik=<%=u.getIdUporabnik() %>">Dodaj prijatelja</a></td>
				<%} %>
				
				<%if(p[i-1]==1){ %>
				<td>Že prijatelja</td>
				<%} %>
				
				<%if(p[i-1]==-1){ %>
				<td>Zavrnjeno</td>
				<%} %>
				
				<%if(p[i-1]==0){ %>
				<td>V čakanju</td>
				<%} %>
				
				</tr>
				<%
				i++;
				self=false;
			} 
		}
	%>
	</table>
	
	
</body>
</html>