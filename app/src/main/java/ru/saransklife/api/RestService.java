package ru.saransklife.api;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;
import ru.saransklife.api.model.LoginResponse;
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

	@FormUrlEncoded
	@POST("/login")
	LoginResponse getLogin(@Field("appVersion") String appVersion,
						   @Field("osInformation") String osInformation,
						   @Field("deviceType") String deviceType,
						   @Field("lang") String lang);

	@GET("/menu")
	MenuResponse getMenu(@QueryMap Map<String, String> options);

	@GET("/page/{slug}")
	PageResponse getPage(@Path("slug") String slug, @QueryMap Map<String, String> options);

	@GET("/place/interesting")
	PlaceEntitiesResponse getInterestingPlaces(@QueryMap Map<String, String> options);

	@GET("/place/categories")
	PlaceCategoriesResponse getPlaceCategories(@QueryMap Map<String, String> options);

	@GET("/place/categories/{slug}")
	PlaceCategoriesResponse getSubPlaceCategories(@Path("slug") String slug, @QueryMap Map<String, String> options);

	@GET("/place/category/{category}")
	PlaceEntitiesResponse getPlaceEntities(@Path("category") String category, @QueryMap Map<String, String> options);

	@GET("/event/categories")
	EventCategoriesResponse getEventCategories(@QueryMap Map<String, String> options);

	@GET("/event")
	EventsResponse getEvents(@QueryMap Map<String, String> options);

	@FormUrlEncoded
	@POST("/place/{id}/rating")
	PostResponse setPlaceRating(@Path("id") long id,
								@Field("deviceId") String deviceId,
								@Field("rating") int rating,
								@Field("token") String token);

	@FormUrlEncoded
	@POST("/place/{id}/recommended")
	PostResponse setPlaceRecommended(@Path("id") long id,
									 @Field("deviceId") String deviceId,
									 @Field("token") String token);

	@FormUrlEncoded
	@POST("/place/{id}/view")
	PostResponse setPlaceView(@Path("id") long id,
							  @Field("deviceId") String deviceId,
							  @Field("token") String token);

	@GET("/reference")
	ReferenceCategoriesResponse getReferenceCategories(@QueryMap Map<String, String> options);

	@GET("/reference/category/{slug}")
	ReferencesResponse getReferences(@Path("slug") String slug, @QueryMap Map<String, String> options);

}
