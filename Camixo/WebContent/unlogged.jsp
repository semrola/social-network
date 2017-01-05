<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dbJPA.Uporabnik, javax.servlet.http.Cookie" %>
<%
Uporabnik u=(Uporabnik)session.getAttribute("prijava");
if(u==null){
	//preverja, ce nisi prijavljen; za strani katere potrebujejo prijavljenega uporabnika
	%>
	<jsp:forward page="index.jsp?p=predstavitev.jsp" />
	<%
}
%>