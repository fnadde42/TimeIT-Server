package se.solit.timeit.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import se.solit.timeit.serializers.DateAsTimestampDeserializer;
import se.solit.timeit.serializers.DateAsTimestampSerializer;
import se.solit.timeit.serializers.TaskSerializer;
import se.solit.timeit.serializers.UserSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@XmlRootElement(name = "Task")
public class Task
{
	@Id
	@Column(nullable = false)
	private String	id;
	private String	name;
	@JsonSerialize(using = TaskSerializer.class)
	private Task	parent;
	private boolean	completed;

	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = DateAsTimestampSerializer.class)
	@JsonDeserialize(using = DateAsTimestampDeserializer.class)
	private Date	lastChange;
	private boolean	deleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner", nullable = false)
	@JsonSerialize(using = UserSerializer.class)
	private User	owner;

	protected Task()
	{
	}

	public Task(final String paramID, final String paramName, final Task paramParent, final boolean paramCompleted,
			final Date paramLastChanged, final boolean paramDeleted, final User paramOwner)
	{
		init(paramID, paramName, paramParent, paramCompleted, paramLastChanged, paramDeleted, paramOwner);
	}

	private void
			init(final String paramID, final String paramName, final Task paramParent, final boolean paramCompleted,
					final Date paramLastChange, final boolean paramDeleted, final User paramOwner)
	{
		if (paramID == null)
		{
			throw new NullPointerException("id is not allowed to be null");
		}
		id = paramID;
		name = paramName;
		parent = paramParent;
		lastChange = paramLastChange;
		completed = paramCompleted;
		deleted = paramDeleted;
		setOwner(paramOwner);
		lastChange = paramLastChange;
	}

	public final void setLastChange(final Date lastChange2)
	{
		this.lastChange = lastChange2;
	}

	public final void setDeleted(final boolean deleted2)
	{
		this.deleted = deleted2;
	}

	public final String getID()
	{
		return id;
	}

	public final String getName()
	{
		return name;
	}

	public final Task getParent()
	{
		return parent;
	}

	public final Date getLastChange()
	{
		return lastChange;
	}

	public final boolean getDeleted()
	{
		return deleted;
	}

	public final boolean getCompleted()
	{
		return completed;
	}

	// CHECKSTYLE:OFF
	// SONAR:OFF
	@Override
	public final boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Task other = (Task) obj;
		if (completed != other.completed)
		{
			return false;
		}
		if (deleted != other.deleted)
		{
			return false;
		}
		if (!id.equals(other.id))
		{
			return false;
		}
		long diff = Math.abs(lastChange.getTime() - other.lastChange.getTime());
		if (diff > 1000)
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (owner.getUsername().equals(other.owner.getUsername()) == false)
		{
			return false;
		}
		if (parent == null)
		{
			if (other.parent != null)
			{
				return false;
			}
		}
		else if (!parent.equals(other.parent))
		{
			return false;
		}
		return true;
	}

	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + id.hashCode();
		result = prime * result + lastChange.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + owner.getUsername().hashCode();
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	// SONAR:ON
	// CHECKSTYLE:ON

	public final void setName(final String name2)
	{
		lastChange = new Date();
		name = name2;
	}

	public final void setParent(final Task parent2)
	{
		lastChange = new Date();
		parent = parent2;
	}

	public final void setCompleted(final boolean completed2)
	{
		lastChange = new Date();
		completed = completed2;
	}

	public final User getOwner()
	{
		return owner;
	}

	public final void setOwner(final User owner2)
	{
		if (owner2 == null)
		{
			throw new NullPointerException("Owner is not allowed to be null");
		}
		owner = owner2;
	}

}
