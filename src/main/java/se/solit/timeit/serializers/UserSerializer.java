package se.solit.timeit.serializers;

import java.io.IOException;

import se.solit.timeit.entities.User;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UserSerializer extends JsonSerializer<User>
{

	@Override
	public void serialize(User value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException
	{
		jgen.writeStartObject();
		jgen.writeStringField("username", value.getUsername());
		jgen.writeEndObject();

	}
}
