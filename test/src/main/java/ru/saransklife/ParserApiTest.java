package ru.saransklife;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import ru.saransklife.place_entities.PlaceEntitiesResponse;


/**
 * Created by asavinova on 27/10/14.
 */
public class ParserApiTest {

	public static void main(String[] args) {

//		new ParserApiTest().testMenu();
		new ParserApiTest().testPlaceEntities();
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

}
