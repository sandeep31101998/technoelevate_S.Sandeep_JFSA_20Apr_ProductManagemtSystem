<%@page import="com.te.productwebapp.beans.Products"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String name = (String) request.getAttribute("name");
List<Products> infoBeans = (List) request.getAttribute("infos");
%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Home</title>
</head>
<body>
	<fieldset>
		<h1 style="color: maroon; text-decoration: underline;">
			Welcome 
		<%=name%></h1>
	</fieldset>

	<%
		if (infoBeans != null) {
	%>
	<fieldset>
		<legend>All Records</legend>
		<table  border="1" style="width:100%">
			<tr>
				<th>PID</th>
				<th>PName</th>
				<th>mgDate</th>
				<th>exDate</th>
				<th>Price</th>
				<th>Quantity</th>
			</tr>
			<%
				for (Products infoBean : infoBeans) {
			%>
			<tr>
				<td style="text-align: center"><%=infoBean.getPid()%></td>
				<td style="text-align: center"><%=infoBean.getPname()%></td>
				<td style="text-align: center"><%=infoBean.getMgDate()%></td>
				<td style="text-align: center"><%=infoBean.getExDate()%></td>
				<td style="text-align: center"><%=infoBean.getPrice()%></td>
				<td style="text-align: center"><%=infoBean.getQuantity()%></td>
			</tr>
			<%
				}
			%>
		</table>

	</fieldset>
	<%
		}
	%>
</body>
</html>