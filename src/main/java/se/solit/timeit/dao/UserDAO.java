package se.solit.timeit.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.Time;
import se.solit.timeit.entities.User;

public class UserDAO
{
	private final EntityManagerFactory	emf;

	public UserDAO(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	@SuppressWarnings("unchecked")
	public Collection<User> getUsers()
	{
		EntityManager em = emf.createEntityManager();
		Collection<User> result = em.createQuery("SELECT u FROM User u").getResultList();
		em.close();
		return result;
	}

	public void add(User user)
	{
		if (user.getUsername().length() == 0)
		{
			throw new IllegalArgumentException("Username must have non zero length");
		}
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

	public User getUser(String username)
	{
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, username);
		em.close();
		return user;
	}

	public void update(User user)
	{
		EntityManager em = emf.createEntityManager();
		if (em.find(User.class, user.getUsername()) == null)
		{
			throw new IllegalArgumentException("User does not exist");
		}
		try
		{
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}
		finally
		{
			em.close();
		}
	}

	public void delete(User user)
	{
		EntityManager em = emf.createEntityManager();
		try
		{
			em.getTransaction().begin();
			removeAllTimes(user.getUsername(), em);
			removeAllTasks(user.getUsername(), em);
			em.remove(em.getReference(User.class, user.getUsername()));
			em.getTransaction().commit();
		}
		finally
		{
			em.close();
		}
	}

	private void removeAllTimes(String username, EntityManager em)
	{
		List<Time> times = TimeDAO.iGetTimes(username, em);
		for (Time time : times)
		{
			em.remove(time);
		}
		em.flush();
	}

	private void removeAllTasks(String username, EntityManager em)
	{
		List<Task> tasks = TaskDAO.iGetTasks(username, em, false);
		for (Task task : tasks)
		{
			em.remove(task);
		}
		tasks = TaskDAO.iGetTasks(username, em, true);
		for (Task task : tasks)
		{
			em.remove(task);
		}

		em.flush();
	}

}
