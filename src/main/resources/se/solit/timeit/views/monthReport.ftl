<#-- @ftlvariable name="" type="se.solit.dwtemplate.resources.MonthReportView" -->
<!DOCTYPE HTML>
<html>
<head>
<#include "head.ftl">
</head>
<body class="report">
	<#include "top.ftl">
	${tabs(0)}
	<div id="MonthReport" class="report">
		<div id="dateBar">
		${previousMonthLink}<div id="month">${month}</div>${nextMonthLink} ${previousYearLink}<div id="year">${year}</div>${nextYearLink}
		</div>
	<p>
	<p>
	<h2>Totals</h2>
	<hr>
	<table class="timeTable" cellspacing=0>
        	<#list allTimes as entry>
        		<tr>
        		<td>
        		</td>
        		<td>
        		<td>
        		<td class="taskName">
        		${entry.key}
        		</td>
        		<td class="duration">
        		${entry.value}
        		</td>
        		<td></td>
        		</tr>
			</#list>
	</table>
	</p>
	<h2>Details</h2>
	<hr>
	<table class="timeTable" cellspacing=0>
	<#list 1..daysInMonth as d>
	<#assign day=getDay(d)>
	<tr class="dayRow ${day}"><td class="dayOfMonth">${d}</td><td class="${day}">${day}</td><td></td><td></td><td></td><td class="lastColumn"></td></tr>
        	<#list getTimes(d) as entry>
        		<tr class="${day}">
        		<td>
        		</td>
        		<td>
        		<td>
        		<td class="taskName">
        		${entry.key}
        		</td>
        		<td class="duration">
        		${entry.value}
        		</td>
        		<td></td>
        		</tr>
			</#list>
	</#list>
	</table>
	</p>
	</div>
	<#include "bottom.ftl">
</body>
</html>