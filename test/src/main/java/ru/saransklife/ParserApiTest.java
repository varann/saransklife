package ru.saransklife;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ru.saransklife.client.event.EventCategoriesResponse;
import ru.saransklife.client.event.EventsResponse;
import ru.saransklife.place_entities.PlaceEntitiesResponse;


/**
 * Created by asavinova on 27/10/14.
 */
public class ParserApiTest {

	public static void main(String[] args) {

//		new ParserApiTest().testMenu();
//		new ParserApiTest().testPlaceEntities();

//		new ParserApiTest().testEventCategories();
		new ParserApiTest().testEvents();
	}

	void testMenu() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			//URL resource = getClass().getResource("menu.json");
			InputStream resourceAsStream = new FileInputStream("menu.json");
			MenuResponse response = mapper.readValue(resourceAsStream, MenuResponse.class);
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void testPlaceEntities() {

		ObjectMapper mapper = new ObjectMapper();
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			InputStream resourceAsStream = new FileInputStream("place_entities.json");
			PlaceEntitiesResponse response = mapper.readValue(resourceAsStream, PlaceEntitiesResponse.class);
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void testEventCategories() {

		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream resourceAsStream = new FileInputStream("event_categories.json");
			EventCategoriesResponse response = mapper.readValue(resourceAsStream, EventCategoriesResponse.class);
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void testEvents() {

		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream resourceAsStream = new FileInputStream("events.json");
			EventsResponse response = mapper.readValue(resourceAsStream, EventsResponse.class);
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
