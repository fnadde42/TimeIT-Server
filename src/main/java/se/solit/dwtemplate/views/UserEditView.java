package se.solit.dwtemplate.views;

import com.google.common.base.Charsets;

import io.dropwizard.views.View;

public class UserEditView extends View
{
	public UserEditView()
	{
		super("useredit.ftl", Charsets.UTF_8);
	}
}
