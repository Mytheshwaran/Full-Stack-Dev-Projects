<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.dhyan.common.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registered Users</title>
</head>
<body>

	<div align="center">
		<h1>Successfully Registered....</h1>
		<table border="1">
			<tr>
				<th>Users</th>
			</tr>
			<%
						RegistrationRemote registerObj = (RegistrationRemote) request.getAttribute("RegistrationRemote");
										for (UserDetails user : registerObj.getUserList()) {
			%>
			<tr>
				<td>
					<%
						out.println(user.getName());
					%>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<a href=index.html><button>Add User</button></a>
</body>
</html>