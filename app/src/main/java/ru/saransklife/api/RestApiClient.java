package ru.saransklife.api;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import ru.saransklife.api.model.MenuResponse;
import ru.saransklife.api.model.PageResponse;
import ru.saransklife.api.model.PlaceCategoriesResponse;
import ru.saransklife.dao.PlaceCategory;

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

}
