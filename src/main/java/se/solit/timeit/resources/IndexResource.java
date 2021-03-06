package se.solit.timeit.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.sessions.Session;
import io.dropwizard.views.View;

import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import se.solit.timeit.entities.User;
import se.solit.timeit.views.IndexView;
import se.solit.timeit.views.LandingView;

import com.sun.jersey.api.core.HttpContext;

@Path("/")
public class IndexResource extends BaseResource
{
	private final EntityManagerFactory	emf;

	public IndexResource(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	@GET
	@Produces("text/html;charset=UTF-8")
	public View landingPage(@Auth(required = false) User user, @Context HttpContext context,
			@Session HttpSession session)
	{
		if (user == null)
		{
			return new LandingView(context, session);
		}
		else
		{
			return new IndexView(user, emf, context, session);
		}
	}

	@GET
	@Path("/login")
	@Produces("text/html;charset=UTF-8")
	public View loginPage(@Auth User user) throws URISyntaxException
	{
		throw redirect("/");
	}
}
