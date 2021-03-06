package DAO;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import se.solit.timeit.dao.TimeDescriptor;
import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.User;

public class TestTimeDescriptor
{
	private static User				owner		= new User("owner", "Owner", "password", "email", null);
	private static DateTime			changeTime	= DateTime.now();
	private static UUID				parentID	= UUID.randomUUID();
	private static UUID				childID		= UUID.randomUUID();
	private static TimeDescriptor	timeDescriptor1;
	private static TimeDescriptor	timeDescriptor2;
	private static TimeDescriptor	timeDescriptor3;
	private static Duration			duration;
	private static Duration			duration2;
	private static Task				parent;
	private static Task				child;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		parent = new Task(parentID, "parent", null, false, changeTime, false, owner);
		child = new Task(childID, "child", parent, false, changeTime, false, owner);
		duration = new Duration(120000);
		duration2 = new Duration(60000);
		timeDescriptor1 = new TimeDescriptor(parent, duration, duration2);
		timeDescriptor2 = new TimeDescriptor(child, duration, duration2);
		timeDescriptor3 = new TimeDescriptor(child, duration, duration2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Test
	public void testGetIndentString()
	{
		assertEquals("&nbsp;&nbsp;&nbsp;", timeDescriptor2.getIndentString());
	}

	@Test
	public void testGetDuration()
	{
		assertEquals(duration, timeDescriptor1.getDuration());
	}

	@Test
	public void testSetDuration()
	{
		Duration newDuration = new Duration(70000);
		timeDescriptor3.setDuration(newDuration);
		assertEquals(newDuration, timeDescriptor3.getDuration());
	}

	@Test
	public void testSetDurationWithChildren()
	{
		Duration newDuration = new Duration(80000);
		timeDescriptor3.setDurationWithChildren(newDuration);
		assertEquals(newDuration, timeDescriptor3.getDurationWithChildren());
	}

	@Test
	public void testGetDurationString()
	{
		assertEquals("00:02", timeDescriptor1.getDurationString());
	}

	@Test
	public void testGetDurationWithChildren()
	{
		assertEquals(duration2, timeDescriptor1.getDurationWithChildren());
	}

	@Test
	public void testGetDurationWithChildrenString()
	{
		assertEquals("00:01", timeDescriptor1.getDurationWithChildrenString());
	}

	@Test
	public void testGetTask()
	{
		assertEquals(parent, timeDescriptor1.getTask());
	}

}
