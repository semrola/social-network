<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.*, java.util.List, db.UporabnikManager" %>
<html>
<head>
<title>Pregled prijateljstev</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="unlogged.jsp" />
	
	<h1>Odprte prošnje za prijateljstva</h1>
	<p class="error">
	<%
		if(request.getAttribute("sms_pregled")!=null)
			out.print(request.getAttribute("sms_pregled")+"<br />");
		if(request.getAttribute("sms_reaction")!=null)
			out.print(request.getAttribute("sms_reaction")+"<br />");
	%>
	</p>
	<table id="table-2">
	<%
		if(request.getAttribute("list_frendov")!=null)
		{
			UporabnikManager um=new UporabnikManager();
			List<Prijateljstvo> list=(List<Prijateljstvo>)request.getAttribute("list_frendov");
			if(list.size()>0) {
				%><thead><th>Št.</th><th>Uporabniško ime</th><th>Ime</th><th>Priimek</th><th>Sprejmi</th><th>Zavrni</th></thead><%
				int i=1;
				for(Prijateljstvo p:list)
				{
					%>
					<tr><td><%=i %></td><td><%=p.getIdUporabnikPobudnik().getUsername() %></td>
					<td><%=p.getIdUporabnikPobudnik().getIme() %></td>
					<td><%=p.getIdUporabnikPobudnik().getPriimek() %></td>
					<td><form action="ObdelavaProsenjZaPrijateljstvoHandlerServlet" method="post"><input type="hidden" value="<%=p.getIdPrijateljstvo() %>" name="prosnja" /><input type="hidden" value="<%=1 %>" name="status" /><input type="image" src="css/img/add1.png" value="Sprejmi" /></form></td>
					<td><form action="ObdelavaProsenjZaPrijateljstvoHandlerServlet" method="post"><input type="hidden" value="<%=p.getIdPrijateljstvo() %>" name="prosnja" /><input type="hidden" value="<%=-1 %>" name="status" /><input type="image" src="css/img/delete1.png" value="Zavrni" /></form></td>
					</tr>
					
					<%			
					i++;
				}
			}
		}
	%>
	</table>
	
</body>
</html>