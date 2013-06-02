<%@ page contentType="text/html; encoding=gb2312"%><%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.RuntimeMXBean" %>
<%
RuntimeMXBean rt =ManagementFactory.getRuntimeMXBean();
%>
<b>Vm Name</b>: <%=rt.getVmName()%><br>
<b>Vm Version</b>: <%=rt.getVmVersion()%><br>
<b>Vm Vendor</b>: <%=rt.getVmVendor()%><br>
<b>Up Time</b>: <%=((float)rt.getUptime())/(1000*60*60)%> hours<br>
<b>Input Arguments</b>: <%=rt.getInputArguments()%><br>
<b>Library Path</b>: <%=rt.getLibraryPath()%><br>
<b>Class Path</b>: <%=rt.getClassPath()%><br>
