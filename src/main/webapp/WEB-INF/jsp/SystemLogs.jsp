<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>System Logs</title>
<script src="/js/security.js"></script>
</head>
<body>
	<%@include file="HPT3.jsp"%>
	<center>
		<h3>System Logs</h3>
	</center>
	<%
		String jspPath = "logs/";
		String fileName = "system.log";
		String txtFilePath = jspPath + fileName;
		BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			out.println(line);
	%>
	<br>
	<%
		}
	%>

</body>
</html>
