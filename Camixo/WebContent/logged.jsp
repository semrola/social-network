<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik" %>
<%
Uporabnik u=(Uporabnik)session.getAttribute("prijava");
if(u!=null){
	//preverja, ce si prijavljen; za prijavo, registracijo...
	%>
	<jsp:forward page="index.jsp?p=predstavitev.jsp" />
	<%
}
%>