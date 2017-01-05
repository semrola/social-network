<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik, javax.servlet.http.Cookie" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Best Social Netwrok In Da World" content="description"/>
<meta name="Camixo; social network" content="keywords"/> 
<meta name="Adrijan&Sandi" content="author"/>
<%

boolean b=false;

if(request.getCookies()!=null)
{
	Cookie[] cookies = request.getCookies();
	Cookie css=null;
	for (int i=0; i<cookies.length; i++) 
		if (cookies[i].getName().equals("css1"))
		{
			//System.out.print("bk");
			css = cookies[i];
			break;
		}
	if(css!=null)
		if(css.getValue().equals("2"))
			b=true;
}
%>


<%if(!b){ %> <link rel="stylesheet" type="text/css" href="css/default.css" media="screen"/> <% }
else{ %><link rel="stylesheet" type="text/css" href="css/other.css" media="screen"/> <% } %>

<title>Camixo Social Network</title>
</head>

<!-- default margin = default layout -->
<body style="margin: 0 12%;">

<div class="container">
	
	<%
	int steviloProsenj=0;
	Uporabnik u=(Uporabnik)session.getAttribute("prijava");
	if((Integer)session.getAttribute("steviloProsenj")!=null)
		steviloProsenj=(Integer)session.getAttribute("steviloProsenj");
	%>
	
	<!-- HEADER -->
	<%@include file="header.jsp" %>
	<div class="stripes"><span></span></div>
	 
	<!-- MENU -->
	<div class="nav">
		<a href="index.jsp?p=predstavitev.jsp">Predstavitev</a>
		<a href="index.jsp?p=PrikazNovicInitServlet">Novice</a>
		<a href="index.jsp?p=urejanje_profil.jsp">Urejanje profila</a>
		<a href="index.jsp?p=iskalnik.jsp">Iskalnik</a>
		<a href="IzpisPrijateljevServlet">Moji prijatelji</a>
		<%if(u!=null && u.getTipUporabnika()==1){%>
		<a href="index.jsp?p=vnosNovic.jsp">Vnos novice</a>
		<a href="index.jsp?p=vnosObvestil.jsp">Vnos obvestil</a>
		<% } %>
		<%if(u==null){ %>
		<a href="index.jsp?p=prijava.jsp">Prijava</a> 
		<a href="index.jsp?p=registracija.jsp">Registracija</a> 
		<% }
		else { %>
		<a href="index.jsp?p=OdjavaHandlerServlet">Odjava</a>
		<div class="logged">
		<table>
			<tr><td><img src="css/img/user_male.png" alt="user"/><%=u.getUsername() %></td></tr>
			<tr><td><a class="noA" href="PregledProsenjZaPrijateljstvoInitServlet"><%if(steviloProsenj>0){ %>Imate <%=steviloProsenj %> prošenj za prijateljstvo<%} else{ %>Nimate prošenj za prijateljstva<%} %></a></td></tr>
		</table>
		</div>
		<%} %>
		<div class="clearer"><span></span></div>		
	</div>
	<div class="stripes"><span></span></div>

	<div class="main">
	
		<div class="left">
		
			<div class="content">
				<%
				String[] pages={"prijava.jsp","registracija.jsp"};	//strani za katere ni treba biti prijavljen
				boolean found=false;
				String s="";
				String p=request.getParameter("p");
				if(p==null)
					if(u==null) s="prijava.jsp";
					else s="predstavitev.jsp";	
				else if(u==null)
				{
					for(int i=0;i<pages.length;i++)
					{	
						if(pages[i].equals(p))
						{
							s=p;							
							found=true;
							break;
						}
						
					if(!found)
						s="prijava.jsp";
					}
				}
				else s=p;
				
				try
				{
					%><jsp:include page="<%=s %>" /><%
				}
				catch(Exception e)
				{
					%><jsp:include page="prijava.jsp" /><%
				}				
				%>
				

			</div>   
		</div>

		<%@include file="rightMenu.jsp" %>

		<div class="clearer"><span></span></div>

	</div>

	<%@include file="footer.jsp" %>

</div>

</body>

</html>