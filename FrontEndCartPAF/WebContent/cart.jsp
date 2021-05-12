<%@page import="com.Cart"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart Service</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/cart.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Cart Service</h1>
				<form id="formCart" name="formCart">
					Research ID: 
					<input id="researchID" name="researchID" type="text"
						class="form-control form-control-sm"> 
						
					<br> Research name:
					<input id="researchName" name="researchName" type="text"
						class="form-control form-control-sm"> 
						
					<br> Amount: 
					<input id="Amount" name="Amount" type="text"
						class="form-control form-control-sm"> 
						
					<br> Description: 
					<input id="Description" name="Description" type="text"
						class="form-control form-control-sm"> 
					
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
				<div id="divItemsGrid">
					<%
						Cart cart = new Cart();
						out.print(cart.readItems());
					%>
				</div>
				
			</div>
		</div>
	</div>
</body>
</html>