package views;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import se.solit.timeit.dao.TaskDAO;
import se.solit.timeit.dao.UserDAO;
import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.User;
import se.solit.timeit.views.Action;
import se.solit.timeit.views.TaskView;

public class TestTaskView
{
	private static EntityManagerFactory	emf			= Persistence.createEntityManagerFactory("test");
	private static User					user2;
	private static Task					task;
	private static UUID					taskID		= UUID.randomUUID();
	private static UUID					parentID	= UUID.fromString("b141b8ff-fa8e-47ff-8631-d86fe97cbc2b");
	private static UUID					childID		= UUID.fromString("c624ba2d-2027-4858-9696-3efc4e4106ad");
	private final HttpSession			session		= Mockito.mock(HttpSession.class);

	@BeforeClass
	public static void beforeClass()
	{
		user2 = new User("minion", "Do Er", "password", "email", null);
		UserDAO userdao = new UserDAO(emf);
		userdao.add(user2);
		task = new Task(taskID, "Name", null, false, DateTime.now(), false, user2);
		Task parent = new Task(parentID, "Parent", null, false, DateTime.now(), false, user2);
		Task child = new Task(childID, "child", parent, false, DateTime.now(), false, user2);
		TaskDAO taskdao = new TaskDAO(emf);
		taskdao.add(parent);
		taskdao.add(child);
	}

	@AfterClass
	public static void afterClass()
	{
		emf.close();
	}

	@Test
	public final void testGetTask()
	{
		TaskView taskView = new TaskView(emf, task, user2, Action.ADD, null, session);
		Assert.assertEquals(task, taskView.getTask());
	}

	@Test
	public final void dummyEnumTest()
	{
		Action.valueOf(Action.ADD.toString());
	}

}
