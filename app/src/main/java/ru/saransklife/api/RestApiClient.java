package ru.saransklife.api;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

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
 * Created by asavinova on 26/10/14.
 */
@Rest(rootUrl = "http://api.trip.pointresearch.ru/1.0/saransk", converters = {MappingJacksonHttpMessageConverter.class})
public interface RestApiClient {

	@Get("/menu")
	MenuResponse getMenu();

	@Get("/page/{slug}")
	PageResponse getPage(String slug);

	@Get("/place/interesting?page=1&limit=10")
	PlaceEntitiesResponse getInterestingPlaces();

	@Get("/place/categories")
	PlaceCategoriesResponse getPlaceCategories();

	@Get("/place/categories/{slug}")
	PlaceCategoriesResponse getSubPlaceCategories(String slug);

	@Get("/place/category/{category}?withChildren=true&showInParent=true&page=1&limit=1000")
	PlaceEntitiesResponse getPlaceEntities(String category);

	@Get("/event/categories")
	EventCategoriesResponse getEventCategories();

	@Get("/event?date={type}&page=1&limit=1000")
	EventsResponse getEvents(String type);

	@Post("/place/{id}/rating?deviceId={deviceId}&rating={rating}")
	PostResponse setPlaceRating(long id, String deviceId, int rating);

	@Post("/place/{id}/recommended?deviceId={deviceId}")
	PostResponse setPlaceRecommended(long id, String deviceId);

	@Post("/place/{id}/view?deviceId={deviceId}")
	PostResponse setPlaceView(long id, String deviceId);

	@Get("/reference")
	ReferenceCategoriesResponse getReferenceCategories();

	@Get("/reference/category/{slug}")
	ReferencesResponse getReferences(String slug);
}
