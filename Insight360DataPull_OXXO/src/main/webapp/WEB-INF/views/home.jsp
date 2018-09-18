<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	HP QC/ALM Data Pull Service  
</h1>

<form action="/">
	<input type="submit" value="Start" id="btnStartPull" name="startPullBtn"/>
</form>

<P>  Data Pull Service started at the ${serverTime}. </P>

</body>
</html>
