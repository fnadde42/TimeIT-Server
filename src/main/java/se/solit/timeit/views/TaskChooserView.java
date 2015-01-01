package se.solit.timeit.views;

import io.dropwizard.views.View;

import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManagerFactory;

import se.solit.timeit.dao.TaskDAO;
import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.User;

import com.google.common.base.Charsets;

public class TaskChooserView extends View
{
	private final User		user;
	private final TaskDAO	taskdao;
	private final Action	type;

	public TaskChooserView(EntityManagerFactory emf, User user, Action type2)
	{
		super("taskChooser.ftl", Charsets.UTF_8);
		this.user = user;
		taskdao = new TaskDAO(emf);
		this.type = type2;
	}

	public boolean isEditMode()
	{
		return type.equals(Action.EDIT);
	}

	public User getCurrentUser()
	{
		return user;
	}

	public List<Entry<String, String>> getTasks() throws SQLException
	{
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		Collection<Task> tasks = taskdao.getTasks(user.getUsername());
		for (Task t : tasks)
		{
			String parentString = getParentString(t);
			list.add(new SimpleEntry<String, String>(t.getID(), parentString));
		}
		return list;
	}

	private String getParentString(Task task2)
	{
		String result = "";
		if (task2.getParent() != null)
		{
			result = getParentString(task2.getParent()).concat("/");
		}
		return result.concat(task2.getName());
	}

}