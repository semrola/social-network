<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@page import="java.util.*" %>
	<%@page import="dbJPA.*" %>
<html>
<head>
<title>izpis_prijateljev</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="unlogged.jsp" />
	<h1>Seznam prijateljev:</h1>
	
	<p class="error">
	<%
		if(request.getAttribute("sms_brisi")!=null)
			out.print(request.getAttribute("sms_brisi")+"<br />");
	%>
	</p>
	
	
	
	<% List<Uporabnik> prijatelji=(List<Uporabnik>)request.getAttribute("prijatelji"); %>
	
	<p class="error">Število prijateljev: <%=prijatelji.size() %></p>
	<%if(prijatelji.size()>0) { %>
	<table id="table-2">
	<thead><th>Št.</th><th>Uporabniško ime</th><th>Ime</th><th>Priimek</th><th>Briši</th></thead>
		<%for(int i=0;i<prijatelji.size();i++){
		%><tr><td><%=i+1 %></td><td><%=prijatelji.get(i).getUsername() %></td><td><%=prijatelji.get(i).getIme() %></td><td><%=prijatelji.get(i).getPriimek() %></td><td><form method="post" action="BrisiPrijateljstvoHandlerServlet"><input type="hidden" name="id_frend" value="<%=prijatelji.get(i).getIdUporabnik() %>" /><input type="image" src="css/img/delete1.png" /></form></td></tr><%
		
		} 
	%>
	</table>
	<%} %>
</body>
</html>