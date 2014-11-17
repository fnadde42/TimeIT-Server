package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.Time;

public class TestTime
{
	private static final Task	task1	= new Task("1", "task1", "parent", false, 0, false, null);
	private static final Task	task2	= new Task("2", "task2", "parent", false, 0, false, null);
	private Time				time;

	@Before
	public void setUp() throws Exception
	{
		time = new Time("1234", 0, 1, false, 0, task1);
	}

	@Test
	public final void testGetUUID()
	{
		assertEquals(time.getUUID(), "1234");
	}

	@Test
	public final void testSetChanged()
	{
		time.setChanged(3);
		assertEquals(time.getChanged(), 3);
	}

	@Test
	public final void testSetTask()
	{
		assertFalse(time.getTask().equals(task2));
		time.setTask(task2);
		assertTrue(time.getTask().equals(task2));
	}

	@Test
	public final void testSetStart()
	{
		time.setStart(123);
		assertEquals(time.getStart(), 123);
	}

	@Test
	public final void testSetStop()
	{
		time.setStop(124);
		assertEquals(time.getStop(), 124);
	}

	@Test
	public final void testSetDeleted()
	{
		assertFalse(time.getDeleted());
		time.setDeleted(true);
		assertTrue(time.getDeleted());
	}

	@Test
	public final void testEqualsObject()
	{
		Time x = new Time("1234", 0, 1, false, 0, task1);
		Time y = new Time("1234", 0, 1, false, 0, task1);
		assertTrue(x.equals(y) && y.equals(x));
		assertTrue(x.hashCode() == y.hashCode());

		y = new Time(null, 0, 1, false, 0, task1);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 1, 1, false, 0, task1);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 0, 2, false, 0, task1);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 0, 1, true, 0, task1);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 0, 1, false, 1, task1);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 0, 1, false, 0, task2);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		y = new Time("1234", 0, 1, false, 0, null);
		assertFalse(x.equals(y));
		assertFalse(y.equals(x));
		assertFalse(x.hashCode() == y.hashCode());

		assertTrue(x.equals(x));
		assertFalse(y.equals(""));
		assertFalse(y.equals(null));

	}

}
