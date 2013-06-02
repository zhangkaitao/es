<%long start = System.currentTimeMillis();%><html>
<head>
<title>JDK1.5 management feature</title>
</head>
<body>
<h1>Java Runtime Info</h1>
<%@include file="runtime.jsp"%><hr>
<h1>JVM OS Info</h1>
<%@include file="OS.jsp"%><hr>
<h1>JVM Memory Info</h1>
<%@include file="memory.jsp"%><hr>
<h1>JVM Thread Info</h1>
<%@include file="thread.jsp"%><hr>
<h1> Execute Cost Time <%=System.currentTimeMillis()-start%> </h1>
</body>
