<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="db.*, dbJPA.*, java.util.*, helper.*" %> 
<html>
<head>
<title>novice</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%
		Uporabnik up=(Uporabnik)session.getAttribute("prijava");
	%>
	
	<jsp:include page="unlogged.jsp" />
	<h1>Novice</h1>
	<p class="error">
	<%
		if(request.getAttribute("sms_PrikazNovic")!=null)
			out.print(request.getAttribute("sms_PrikazNovic"));
		if(request.getAttribute("sms_Brisanje")!=null)
			out.print(request.getAttribute("sms_Brisanje"));
		if(request.getAttribute("sms_Urejanje")!=null)
			for(String s:(ArrayList<String>)request.getAttribute("sms_Urejanje"))
				out.print(s+"<br />");
	%>
	</p>
	
	<%
		if(up.getTipUporabnika()==1)
		{
			%><p><a href="index.jsp?p=vnosNovic.jsp" style="float:right;"><img src="css/img/add1.png" alt="Add" title="Add" /></a></p><%
		} 
	%>
		
	<p>
	<%
		if(request.getAttribute("novice")!=null)
		{
			List<Entiteta> list=(List<Entiteta>)request.getAttribute("novice");
			ArrayList<String> avtorji=(ArrayList<String>)request.getAttribute("avtorji");
			Novica n;
			for(int i=0;i<list.size();i++)
			{
				n=(Novica)list.get(i);	//izpis novic
				%>
				<hr>
				<h2><%=n.getNaziv() %></h2>
				<%if(up.getTipUporabnika()==1)
				{
					%><span style="float:right;"><a href="UrejanjeNoviceInitServlet?id_novica=<%=n.getIdNovica() %>"><img src="css/img/edit2.png" alt="Edit" title="Edit" /></a>&nbsp;<a href="BrisanjeNoviceHandlerServlet?id_novica=<%=n.getIdNovica() %>"><img src="css/img/delete1.png" alt="Delete" title="Delete" /></a></span>
				<% } %>
				
				<div class="descr"><%=Helper.vrniLepoOblikoDatuma(n.getDatumObjave()) %> by  <%=avtorji.get(i) %></div>
				
				<p><%=n.getVsebina() %></p>
				<%
			}
		}
		else
		{
			%><jsp:forward page="index.jsp?p=predstavitev.jsp" /><%
		}
	%>
	</p>
</body>
</html>