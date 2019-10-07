package com.example.demo.service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoInit;


import com.example.demo.security.OpenIdConnectFilter;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import com.google.api.client.json.JsonFactory;

@Service
public class TodoServiceImpl implements TodoService  {
    private static final String APPLICATION_NAME = "Google Calendar SpringBoot";
	private static final String CALENDAR_ID = "primary";

	@Override
	public List<Todo> viewAllEvents() throws IOException {
		// Build a new authorized API client service.

				GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

				Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
						.setApplicationName(APPLICATION_NAME).build();

				// List upcoming events - sorted with starting time
				DateTime now = new DateTime(System.currentTimeMillis());
				Events events = service.events().list(CALENDAR_ID).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
						.execute();

				List<Event> items = events.getItems();
				List<Todo> eventList = new ArrayList<>();

				/* map events to simpler models */
				for (Event event : items) {

					DateTime start = event.getStart().getDateTime();
					if (start == null) {
						start = event.getStart().getDate();
					}

					DateTime end = event.getEnd().getDateTime();
					if (end == null) {
						end = event.getEnd().getDate();
					}

					Todo todo = new Todo(event.getId(), event.getSummary(), event.getDescription(),
							start.toString(), end.toString());

					eventList.add(todo);
				}
				return eventList;
	}


	
	
	public Event addEvent(TodoInit todoInit) throws IOException {
		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());
//		System.out.println(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = new Event().setSummary(todoInit.getSummary()).setDescription(todoInit.getDescription());

		String timeZone = "+05:30";
		String strStartDateTime = todoInit.getStartDate() + "T" + todoInit.getStartTime() + timeZone;
		DateTime startDateTime = new DateTime(strStartDateTime);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		String strEndDateTime = todoInit.getEndDate() + "T" + todoInit.getEndTime() + timeZone;

		DateTime endDateTime = new DateTime(strEndDateTime);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		return service.events().insert(CALENDAR_ID, event).execute();
	}


	@Override
	public Todo viewEventById(String eventId) throws IOException {
		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = service.events().get(CALENDAR_ID, eventId).execute();

		DateTime start = event.getStart().getDateTime();
		if (start == null) {
			start = event.getStart().getDate();
		}

		DateTime end = event.getEnd().getDateTime();
		if (end == null) {
			end = event.getEnd().getDate();
		}

		/* map to a simpler object for response object */
		return new Todo(event.getId(), event.getSummary(), event.getDescription(), start.toString(),end.toString());

	}


	@Override
	public Event updateEvent(String eventId, TodoInit todoInit) throws IOException {
		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		Event event = service.events().get(CALENDAR_ID, eventId).execute();

		event.setSummary(todoInit.getSummary())
		.setDescription(todoInit.getDescription());

		String timeZone = "+05:30";
		String strStartDateTime = todoInit.getStartDate() + "T" + todoInit.getStartTime() + timeZone;

		DateTime startDateTime = new DateTime(strStartDateTime);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		String strEndDateTime = todoInit.getEndDate() + "T" + todoInit.getEndTime() + timeZone;

		DateTime endDateTime = new DateTime(strEndDateTime);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);

		return service.events().update(CALENDAR_ID, eventId, event).execute();
	}


	@Override
	public void deleteEvent(String eventId) throws IOException {
		GoogleCredential credential = new GoogleCredential().setAccessToken(OpenIdConnectFilter.getToken());

		Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
				.setApplicationName(APPLICATION_NAME).build();

		service.events().delete(CALENDAR_ID, eventId).execute();
		
	}             
}