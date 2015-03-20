package ru.saransklife.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.androidannotations.annotations.EBean;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;
import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.api.model.PostResponse;
import ru.saransklife.api.model.ReferenceCategoriesResponse;
import ru.saransklife.api.model.ReferencesResponse;

/**
 * Created by asavinova on 17/03/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ApiCLient {

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

	public ApiCLient() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateSerializer())
				.registerTypeAdapter(Long.class, new JsonDeserializer<Long>() {
					@Override
					public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
						if (json.isJsonNull() || json.getAsString().isEmpty()) {
							return null;
						}
						return json.getAsLong();
					}
				})
				.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
					@Override
					public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
						if (json.isJsonNull() || json.getAsString().isEmpty()) {
							return null;
						}
						return json.getAsInt();
					}
				})
				.create();

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint("http://api.trip.pointresearch.ru/1.0/saransk")
				.setConverter(new GsonConverter(gson))
				.build();

		service = restAdapter.create(RestService.class);
	}

	public MenuResponse getMenu() {
		return service.getMenu();
	}

	public PageResponse getPage(String slug) {
		return service.getPage(slug);
	}

	public PlaceEntitiesResponse getInterestingPlaces() {
		Map<String, String> options = new HashMap<>();
		options.put("page", "1");
		options.put("limit", "10");
		return service.getInterestingPlaces(options);
	}

	public PlaceCategoriesResponse getPlaceCategories() {
		return service.getPlaceCategories();
	}

	public PlaceCategoriesResponse getSubPlaceCategories(String slug) {
		return service.getSubPlaceCategories(slug);
	}

	public PlaceEntitiesResponse getPlaceEntities(String category) {
		Map<String, String> options = new HashMap<>();
		options.put("withChildren", "true");
		options.put("showInParent", "true");
		options.put("page", "1");
		options.put("limit", "1000");
		return service.getInterestingPlaces(options);
	}

	public EventCategoriesResponse getEventCategories() {
		return service.getEventCategories();
	}

	public EventsResponse getEvents(String type) {
		Map<String, String> options = new HashMap<>();
		options.put("date", type);
		options.put("page", "1");
		options.put("limit", "1000");
		return service.getEvents(options);
	}

	public PostResponse setPlaceRating(long id, String deviceId, int rating) {
		return service.setPlaceRating(id, deviceId, rating);
	}

	public PostResponse setPlaceRecommended(long id, String deviceId) {
		return service.setPlaceRecommended(id, deviceId);
	}

	public PostResponse setPlaceView(long id, String deviceId) {
		return service.setPlaceView(id, deviceId);
	}

	public ReferenceCategoriesResponse getReferenceCategories() {
		return service.getReferenceCategories();
	}

	public ReferencesResponse getReferences(String slug) {
		return service.getReferences(slug);
	}
}
