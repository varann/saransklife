package ru.saransklife.api;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;
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
public interface RestService {

	@GET("/menu")
	MenuResponse getMenu();

	@GET("/page/{slug}")
	PageResponse getPage(@Path("slug") String slug);

	@GET("/place/interesting")
	PlaceEntitiesResponse getInterestingPlaces(@QueryMap Map<String, String> options);

	@GET("/place/categories")
	PlaceCategoriesResponse getPlaceCategories();

	@GET("/place/categories/{slug}")
	PlaceCategoriesResponse getSubPlaceCategories(@Path("slug") String slug);

	@GET("/place/category/{category}")
	PlaceEntitiesResponse getPlaceEntities(String category, @QueryMap Map<String, String> options);

	@GET("/event/categories")
	EventCategoriesResponse getEventCategories();

	@GET("/event")
	EventsResponse getEvents(@QueryMap Map<String, String> options);

	@POST("/place/{id}/rating")
	PostResponse setPlaceRating(@Path("id") long id, @Field("deviceId") String deviceId, @Field("rating") int rating);

	@POST("/place/{id}/recommended?deviceId={deviceId}")
	PostResponse setPlaceRecommended(@Path("id") long id, @Field("deviceId") String deviceId);

	@POST("/place/{id}/view?deviceId={deviceId}")
	PostResponse setPlaceView(@Path("id") long id, @Field("deviceId") String deviceId);

	@GET("/reference")
	ReferenceCategoriesResponse getReferenceCategories();

	@GET("/reference/category/{slug}")
	ReferencesResponse getReferences(@Path("slug") String slug);

}
