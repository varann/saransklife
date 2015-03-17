package ru.saransklife.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.androidannotations.annotations.EBean;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ru.saransklife.api.model.EventsResponse;

/**
 * Created by asavinova on 17/03/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class API {

	private static final String[] DATE_FORMATS = new String[] {
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd"
	};

	private class DateSerializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement jsonElement, Type typeOF,
								JsonDeserializationContext context) throws JsonParseException {
			for (String format : DATE_FORMATS) {
				try {
					return new SimpleDateFormat(format).parse(jsonElement.getAsString());
				} catch (ParseException e) {
				}
			}
			throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
					+ "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
		}
	}

	private RestService service;

	public API() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateSerializer())
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://api.trip.pointresearch.ru/1.0/saransk")
				.setConverter(new GsonConverter(gson))
				.build();

		service = restAdapter.create(RestService.class);
	}

	public EventsResponse getEvents(String type) {
		Map<String, String> options = new HashMap<>();
		options.put("date", type);
		options.put("page", "1");
		options.put("limit", "1000");
		return service.getEvents(options);
	}
}
