package ru.saransklife.api;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import ru.saransklife.api.model.EventsResponse;

/**
 * Created by asavinova on 17/03/15.
 */
public interface RestService {

	@GET("/event")
	EventsResponse getEvents(@QueryMap Map<String, String> options);

}
