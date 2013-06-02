<%@ page contentType="text/html; encoding=gb2312"%><%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.ThreadInfo" %>
<%@ page import="java.lang.management.ThreadMXBean" %>
<%
ThreadMXBean tm = ManagementFactory.getThreadMXBean();
tm.setThreadContentionMonitoringEnabled(true);
%>
<b>Thread Count: </b><%=tm.getThreadCount()%><br>
<b>Started Thread Count: </b><%=tm.getTotalStartedThreadCount()%><br>
<b>thread contention monitoring is enabled? </b><%=tm.isThreadContentionMonitoringEnabled()%><br>
<b>if the Java virtual machine supports thread contention monitoring? </b><%=tm.isThreadContentionMonitoringSupported()%><br>
<b>thread CPU time measurement is enabled? </b><%=tm.isThreadCpuTimeEnabled()%><br>
<b>if the Java virtual machine implementation supports CPU time measurement for any thread? </b><%=tm.isThreadCpuTimeSupported()%><br>
<hr>
<%
long [] tid = tm.getAllThreadIds();
ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);

long [][] threadArray = new long[tia.length][2];

for (int i = 0; i < tia.length; i++) {          
    long threadId = tia[i].getThreadId();

    long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
    threadArray[i][0] = threadId;
    threadArray[i][1] = cpuTime;
}

long [] temp = new long[2];
for (int j = 0; j < threadArray.length - 1; j ++){
	for (int k = j + 1; k < threadArray.length; k++ )
    if (threadArray[j][1] < threadArray[k][1]){
        temp = threadArray[j];
        threadArray[j] = threadArray[k];
        threadArray[k] = temp;  
    }
}

for (int t = 0; t < threadArray.length; t ++)
{
  ThreadInfo ti = tm.getThreadInfo(threadArray[t][0],Integer.MAX_VALUE);
  if (ti == null) continue;
%>
<b>Thread ID: </b><%=threadArray[t][0]%><br>
<b>Thread Name: </b><%=ti.getThreadName()%><br>
<b>Thread State: </b><%=ti.getThreadState()%><br>
<b>Thread Lock Name: </b><%=ti.getLockName()%><br>
<b>Thread Lock Owner Name: </b><%=ti.getLockOwnerName()%><br>
<b>Thread CPU Time: </b><%=threadArray[t][1]%> sec<br>
<b>Stack Info: (depth:<%=ti.getStackTrace().length%>)</b><br>
<%
StackTraceElement[] stes = ti.getStackTrace();
for(int j=0; j<stes.length; j++)
{
  StackTraceElement ste = stes[j];
%>
&nbsp;&nbsp;&nbsp;&nbsp;+<%=ste%><br>
<%
}
%>
<hr>
<%
}
%>
