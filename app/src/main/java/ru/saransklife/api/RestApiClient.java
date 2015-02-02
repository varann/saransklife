package ru.saransklife.api;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import ru.saransklife.api.model.EventCategoriesResponse;
import ru.saransklife.api.model.EventsResponse;
import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.api.model.PlaceEntitiesResponse;

/**
 * Created by asavinova on 26/10/14.
 */
@Rest(rootUrl = "http://api.trip.pointresearch.ru/1.0/saransk", converters = { MappingJacksonHttpMessageConverter.class })
public interface RestApiClient {

	@Get("/menu")
	MenuResponse getMenu();

	@Get("/page/{slug}")
	PageResponse getPage(String slug);

	@Get("/place/categories")
	PlaceCategoriesResponse getPlaceCategories();

	@Get("/place/category/{category}")
	PlaceEntitiesResponse getPlaceEntities(String category);

	@Get("/event/categories")
	EventCategoriesResponse getEventCategories();

	@Get("/event?date=week&page=1&limit=10")
	EventsResponse getEvents();
}
