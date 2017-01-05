<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik, java.util.ArrayList" %>
<html>
<head>
<title>Registracija</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<p class="error">
	<jsp:include page="logged.jsp" />
	<%
		if(request.getAttribute("smsarray")!=null){
		
		@SuppressWarnings("unchecked")
		ArrayList<String> sms=(ArrayList<String>)request.getAttribute("smsarray");
			for(int i =0; i<sms.size();i++){
			%>
			<%=sms.get(i) %><br />
			<%
			}
		}
		
		String p="";
	%>
	</p>
	<p>Z zvezdico* - obvezno
	<form method="post" action="RegistracijaHandlerServlet">
		<table>
		<tr><td>Uporabniško ime*</td><td><input type="text" name="username" value="<%if((p=request.getParameter("username"))!=null) out.print(p); %>"  /></td></tr>
		<tr><td>Ime*</td><td><input type="text" name="ime" value="<%if((p=request.getParameter("ime"))!=null) out.print(p); %>" /></td></tr>
		<tr><td>Priimek*</td><td><input type="text" name="priimek" value="<%if((p=request.getParameter("priimek"))!=null) out.print(p); %>"  /></td></tr>
		<tr>
			<td>Spol</td>
			<td>
				<input type="radio" name="spol" value="M" <%if((p=request.getParameter("spol"))!=null) if(p.equals("M")){ %> checked <%} %> />M
				<input type="radio" name="spol" value="Z" <%if((p=request.getParameter("spol"))!=null) if(p.equals("Z")){ %> checked <%} %>/>Ž
			</td>
		</tr>
		<tr><td>Naslov*</td><td><input type="text" name="naslov" value="<%if((p=request.getParameter("naslov"))!=null) out.print(p); %>" /></td></tr>
		<tr><td>Vrsta študija*</td>
			<td>
			<select name="studij">
				<option <%if((p=request.getParameter("studij"))!=null) if(p.equals("redno")){ %> selected <%} %> >redno</option>
				<option <%if((p=request.getParameter("studij"))!=null) if(p.equals("izredno")){ %> selected <%} %> >izredno</option>
				<option <%if((p=request.getParameter("studij"))!=null) if(p.equals("pavzer")){ %> selected <%} %> >pavzer</option>
			</select>
			</td>
		</tr>
		<tr><td>Stopnja študija*</td>
			<td>
			<select name="stopnja">
				<option <%if((p=request.getParameter("stopnja"))!=null) if(p.equals("prva")){ %> selected <%} %> >prva</option>
				<option <%if((p=request.getParameter("stopnja"))!=null) if(p.equals("druga")){ %> selected <%} %> >druga</option>
				<option <%if((p=request.getParameter("stopnja"))!=null) if(p.equals("tretja")){ %> selected <%} %> >tretja</option>
			</select>
			</td>
		</tr>
		<tr><td>Študijska smer*</td>
			<td>
			<select name="smer">
				<option <%if((p=request.getParameter("smer"))!=null) if(p.equals("UN")){ %> selected <%} %> >UN</option>
				<option <%if((p=request.getParameter("smer"))!=null) if(p.equals("VS")){ %> selected <%} %> >VS</option>
				<option <%if((p=request.getParameter("smer"))!=null) if(p.equals("VŠ")){ %> selected <%} %> >VŠ</option>
			</select>
			</td>
		</tr>
		<tr><td>Leto rojstva</td><td><input type="text" name="rojstvo" value="<%if((p=request.getParameter("rojstvo"))!=null) out.print(p); %>" /></td></tr>
		<tr><td>Leto diplomiranja*</td><td><input type="text" name="dipl" value="<%if((p=request.getParameter("dipl"))!=null) out.print(p); %>"  /></td></tr>
		<tr><td>E-Mail*</td><td><input type="text" name="email" value="<%if((p=request.getParameter("email"))!=null) out.print(p); %>" /></td></tr>
		<tr><td>Geslo*</td><td><input type="password" name="pass1" /></td></tr>
		<tr><td>Ponovi geslo*</td><td><input type="password" name="pass2" /></td></tr>
		<tr><td></td><td><input type="submit" value="Registriraj me" /></td></tr>
		</table>
	</form>
	</p>

</body>
</html>