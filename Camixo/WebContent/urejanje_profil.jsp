<!DOCTYPE HTML><%@page import="java.util.ArrayList"%>
<%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik, java.util.ArrayList" %>
<html>
<head>
<title>Urejanje profila</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
	<h1>Urejanje profila</h1>
	<p class="error">
	<%
		if(request.getAttribute("smsarray")!=null)
			for(String s:(ArrayList<String>)request.getAttribute("smsarray"))
				out.print(s+"<br />");
	 %>
	 </p>
	
	<jsp:useBean id="prijava" type="dbJPA.Uporabnik" scope="session" />
		<form method="post" action="UrejanjeProfilServlet">
			<table>
			<tr><td>Ime</td><td><input type="text" name="ime" value="<jsp:getProperty property="ime" name="prijava"/>" /></td></tr>
			<tr><td>Priimek</td><td><input type="text" name="priimek" value="<jsp:getProperty property="priimek" name="prijava"/>"/></td></tr>
			<tr><td>Spol</td>
				<td>
				<%
				Uporabnik up=(Uporabnik)session.getAttribute("prijava");
				String spol=up.getSpol();
				%>
				<input type="radio" name="spol" value="M" <%if(spol.equals("M")){ %> checked <%} %> />M
				<input type="radio" name="spol" value="Z" <%if(spol.equals("Z")){ %> checked <%} %>/>Ž
				</td>
			</tr>
			<tr><td>Naslov</td><td><input type="text" name="naslov" value="<jsp:getProperty property="naslov" name="prijava"/>"/></td></tr>
			
			<tr><td>Vrsta študija</td>
				<td>
				<%
				String studij=up.getVrstaStudija();
				%>
				<select name="studij">				
					<option <%if(studij.equals("redno")){%>selected="selected"<%} %> value="redno">redno</option>
					<option <%if(studij.equals("izredno")){%>selected="selected"<%} %> value="izredno">izredno</option>
					<option <%if(studij.equals("pavzer")){%>selected="selected"<%} %> value="pavzer">pavzer</option>
				</select>
				</td>
			</tr>
			
			<tr><td>Stopnja študija</td>
				<td>
				<%
				String stopnja=up.getStopnjaStudija();
				%>
				<select name="stopnja">
					<option <%if(stopnja.equals("prva")){%>selected="selected"<%} %> value="prva">prva</option>
					<option <%if(stopnja.equals("druga")){%>selected="selected"<%} %> value="druga">druga</option>
					<option <%if(stopnja.equals("tretja")){%>selected="selected"<%} %> value="tretja">tretja</option>
				</select>
				</td>
			</tr>
			<tr><td>Študijska smer</td>
				<td>
				<%
				String smer=up.getStudijskaSmer();
				%>
				<select name="smer">
					<option <%if(smer.equals("UN")){%>selected="selected"<%} %> value="UN">UN</option>
					<option <%if(smer.equals("VS")){%>selected="selected"<%} %> value="VS">VS</option>
					<option <%if(smer.equals("VŠ")){%>selected="selected"<%} %> value="VŠ">VŠ</option>
				</select>
				</td>
			</tr>
			<tr><td>Leto rojstva</td><td><input type="text" name="rojstvo" value="<jsp:getProperty property="datumRojstva" name="prijava"/>"/></td></tr>
			<tr><td>Leto diplomiranja</td><td><input type="text" name="dipl" value="<jsp:getProperty property="datumDiplome" name="prijava"/>"/></td></tr>
			<tr><td>E-Mail</td><td><input type="text" name="email" value="<jsp:getProperty property="email" name="prijava"/>"/></td></tr>
			<tr><td>Geslo</td><td><input type="password" name="pass1" value="" /></td></tr>
			<tr><td>Ponovi geslo</td><td><input type="password" name="pass2" /></td></tr>
			<tr><td>Željeni CSS</td>
				<td>
					<input type="radio" name="css" value="1" checked />Temni
					<input type="radio" name="css" value="2" />Svetli
				</td>
				<td style="font-size-adjust: 0.45;">Po menjavi CSS predloge je potrebno osvežiti stran oz. klikniti na eno povezavo v meniju</td>
			</tr>
			
			<tr><td></td><td><input type="submit" value="Posodobi profil" /></td></tr>
			</table>
		</form>
		
		<!-- <form action="UrejanjeProfilServlet" method="get">
		<input type="hidden" name="izvoz" value="html" />
		<input type="submit" value="Izvoz v HTML" />
		</form> -->
		<a href="UrejanjeProfilServlet?izvoz=html">Izvoz v HTML</a>
		<a href="UrejanjeProfilServlet?izvoz=excel">Izvoz v Excel</a>
		<a href="IzpisPrijateljevServlet">Izpiši prijatelje</a>
		<%
		if(request.getAttribute("css")!=null)
		{
			//System.out.print("wasu");
			request.removeAttribute("css");
			%>
			<jsp:forward page="index.jsp?p=urejanje_profil.jsp" />
			<% 
		} 
		%>
		
</html>