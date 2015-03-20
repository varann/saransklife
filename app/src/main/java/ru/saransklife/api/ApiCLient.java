package ru.saransklife.api;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import ru.saransklife.BuildConfig;
import ru.saransklife.api.model.EventsResponse;
import ru.saransklife.api.model.LoginResponse;
import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;
import ru.saransklife.api.model.PostResponse;
import ru.saransklife.api.model.ReferenceCategoriesResponse;
import ru.saransklife.api.model.ReferencesResponse;
import ru.saransklife.client.Preferences_;

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


	@Pref Preferences_ preferences;
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
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.setEndpoint("http://api.trip.pointresearch.ru/1.0/saransk")
				.setConverter(new GsonConverter(gson))
				.build();

		service = restAdapter.create(RestService.class);
	}

	private Map<String, String> getTokenOptions() {
		Map<String, String> options = new HashMap<>();
		options.put("token", preferences.token().get());
		return options;
	}

	public LoginResponse getLogin() {

		String version = BuildConfig.VERSION_NAME;
		String osInformation = "Android " + Build.VERSION.RELEASE
				+ " (API level " + Build.VERSION.SDK_INT
				+ "), версия ядра " + System.getProperty("os.version");
		String deviceType = Build.BRAND + " " + Build.MODEL
				+ " (" + Build.MANUFACTURER + " " + Build.PRODUCT + ")";

		try {
			return service.getLogin(version, osInformation, deviceType, "ru");
		} catch (RetrofitError error) {
			return null;
		}
	}

	public MenuResponse getMenu() {
		return service.getMenu(getTokenOptions());
	}

	public PageResponse getPage(String slug) {
		return service.getPage(slug, getTokenOptions());
	}

	public PlaceEntitiesResponse getInterestingPlaces() {
		Map<String, String> options = getTokenOptions();
		options.put("page", "1");
		options.put("limit", "10");
		return service.getInterestingPlaces(options);
	}

	public PlaceCategoriesResponse getPlaceCategories() {
		return service.getPlaceCategories(getTokenOptions());
	}

	public PlaceCategoriesResponse getSubPlaceCategories(String slug) {
		return service.getSubPlaceCategories(slug, getTokenOptions());
	}

	public PlaceEntitiesResponse getPlaceEntities(String category) {
		Map<String, String> options = getTokenOptions();
		options.put("withChildren", "true");
		options.put("showInParent", "true");
		options.put("page", "1");
		options.put("limit", "1000");
		return service.getPlaceEntities(category, options);
	}

	public EventsResponse getEvents(String type) {
		Map<String, String> options = getTokenOptions();
		options.put("date", type);
		options.put("page", "1");
		options.put("limit", "1000");
		return service.getEvents(options);
	}

	public PostResponse setPlaceRating(long id, String deviceId, int rating) {
		return service.setPlaceRating(id, deviceId, rating, preferences.token().get());
	}

	public PostResponse setPlaceRecommended(long id, String deviceId) {
		return service.setPlaceRecommended(id, deviceId, preferences.token().get());
	}

	public PostResponse setPlaceView(long id, String deviceId) {
		return service.setPlaceView(id, deviceId, preferences.token().get());
	}

	public ReferenceCategoriesResponse getReferenceCategories() {
		return service.getReferenceCategories(getTokenOptions());
	}

	public ReferencesResponse getReferences(String slug) {
		return service.getReferences(slug, getTokenOptions());
	}
}
