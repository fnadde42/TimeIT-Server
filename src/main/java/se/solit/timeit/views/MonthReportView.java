package se.solit.timeit.views;

import javax.persistence.EntityManagerFactory;

import org.joda.time.DateTime;

import se.solit.timeit.dao.TimeDAO;
import se.solit.timeit.dao.TimeDescriptorList;
import se.solit.timeit.entities.User;

import com.sun.jersey.api.core.HttpContext;

public class MonthReportView extends ReportView
{
	private final TimeDAO	timeDAO;

	public MonthReportView(EntityManagerFactory emf, DateTime pointInMonth, User user, User reportedUser,
			HttpContext context)
	{
		super("monthReport.ftl", user, pointInMonth, reportedUser, context);
		timeDAO = new TimeDAO(emf);
	}

	public String getMonth()
	{
		return pointInTime.toString("MMMMMMMMMM");
	}

	public String getDay(int d)
	{
		return pointInTime.withDayOfMonth(d).toString("EEE");
	}

	public int getDaysInMonth()
	{
		return pointInTime.withDayOfMonth(1).plusMonths(1).minusDays(1).getDayOfMonth();
	}

	public TimeDescriptorList getTimes(int day)
	{
		DateTime start = pointInTime.withDayOfMonth(day).withTimeAtStartOfDay();
		DateTime stop = start.withTime(LAST_HOUR_OF_DAY, LAST_MINUTE_OF_HOUR, LAST_SECOND_OF_MINUTE, 0);
		return timeDAO.getTimes(user, start, stop);
	}

	public TimeDescriptorList getAllTimes()
	{
		DateTime start = pointInTime.withDayOfMonth(1).withTimeAtStartOfDay();
		DateTime stop = start.plusMonths(1).minusDays(1)
				.withTime(LAST_HOUR_OF_DAY, LAST_MINUTE_OF_HOUR, LAST_SECOND_OF_MINUTE, 0);
		return timeDAO.getTimes(user, start, stop);
	}

	public String getPreviousMonthLink()
	{
		DateTime nextPointInTime = pointInTime.minusMonths(1);
		return createUrl(nextPointInTime, false);
	}

	public String getPreviousYearLink()
	{
		DateTime nextPointInTime = pointInTime.minusYears(1);
		return createUrl(nextPointInTime, false);
	}

	public String getNextMonthLink()
	{
		DateTime nextPointInTime = pointInTime.plusMonths(1);
		if (nextPointInTime.isAfterNow())
		{
			return "<button type='button' disabled='disabled'>&gt;&gt;</button>";
		}
		else
		{
			return createUrl(nextPointInTime, true);
		}
	}

	public String getNextYearLink()
	{
		DateTime nextPointInTime = pointInTime.plusYears(1);
		if (nextPointInTime.isAfterNow())
		{
			return "<button type='button' disabled='disabled'>&gt;&gt;</button>";
		}
		else
		{
			return createUrl(nextPointInTime, true);
		}
	}

	private String createUrl(DateTime nextPointInTime, boolean forward)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<a href='/report/");
		stringBuilder.append(reportedUser.getUsername());
		stringBuilder.append("/");
		stringBuilder.append(String.valueOf(nextPointInTime.getYear()));
		stringBuilder.append("/");
		stringBuilder.append(String.valueOf(nextPointInTime.getMonthOfYear()));
		stringBuilder.append("'>");
		String link = stringBuilder.toString();
		if (forward)
		{
			return link + "<button type='button'>&gt;&gt;</button></a>";
		}
		else
		{
			return link + "<button type='button'>&lt;&lt;</button></a>";
		}
	}

}
