package resources;

import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import io.dropwizard.views.ViewMessageBodyWriter;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.HttpHeaders;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import se.solit.timeit.application.MyAuthenticator;
import se.solit.timeit.dao.TaskDAO;
import se.solit.timeit.dao.UserDAO;
import se.solit.timeit.entities.Task;
import se.solit.timeit.entities.User;
import se.solit.timeit.resources.IndexResource;

import com.codahale.metrics.MetricRegistry;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class TestIndexResource
{
	private static EntityManagerFactory		emf				= Persistence.createEntityManagerFactory("test");

	private static BasicAuthProvider<User>	myAuthenticator	= new BasicAuthProvider<User>(new MyAuthenticator(emf),
																	"Authenticator");
	private final static HttpSession		mockSession		= Mockito.mock(HttpSession.class);

	@ClassRule
	public static final ResourceTestRule	resources		= ResourceTestRule
																	.builder()
																	.addResource(new IndexResource(emf))
																	.addProvider(
																			new SessionInjectableProvider<HttpSession>(
																					HttpSession.class,
																					mockSession))
																	.addProvider(
																			new ViewMessageBodyWriter(
																					new MetricRegistry()))
																	.addProvider(
																			new ContextInjectableProvider<HttpHeaders>(
																					HttpHeaders.class, null))
																	.addResource(myAuthenticator).build();

	@BeforeClass
	public static void beforeClass()
	{
		UserDAO userDAO = new UserDAO(emf);
		TaskDAO taskDAO = new TaskDAO(emf);
		User user = new User("admin", "Bob B", "password", "email", null);
		userDAO.add(user);
		Task task = new Task(UUID.randomUUID(), "admin stuff", null, false, DateTime.now(), false, user);
		taskDAO.add(task);
	}

	@AfterClass
	public static void afterClass()
	{
		UserDAO userDAO = new UserDAO(emf);
		User user = new User("admin", "Bob B", "password", "email", null);
		userDAO.delete(user);
		emf.close();
	}

	@Test
	public final void testIndexPage()
	{
		Client client = resources.client();
		WebResource resource = client.resource("/");
		resource.addFilter(new HTTPBasicAuthFilter("admin", "password"));
		String actual = resource.accept("text/html").get(String.class);
		Assert.assertTrue(actual.contains("admin stuff"));
	}

	@Test
	public final void testLandingPage()
	{
		Client client = resources.client();
		WebResource resource = client.resource("/");
		String actual = resource.accept("text/html").get(String.class);
		Assert.assertTrue(actual.contains("<H1>TimeIT server</H1>"));
	}

	@Test
	public final void testLoginPage()
	{
		Client client = resources.client();
		WebResource resource = client.resource("/login");
		resource.addFilter(new HTTPBasicAuthFilter("admin", "password"));
		try
		{
			resource.accept("text/html").get(String.class);
			Assert.fail("Should have thrown exception");
		}
		catch (Exception e)
		{
			Assert.assertEquals(UniformInterfaceException.class, e.getClass());
		}
	}

}
