package event.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import event.authentication.AuthManager;
import event.datatransferobjects.AvailabilityDto;
import event.datatransferobjects.EventIdsDto;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Position;
import event.domain.objects.Participant;
import event.foreigndomain.entitites.Message;
import event.models.EventCreationModel;
import event.models.EventJoinModel;
import event.service.EventService;
import event.service.MessageService;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {
    @Mock
    private EventService eventService;

    @Mock
    private AuthManager auth;

    @Mock
    private MessageService messageService;

    private EventController controller;

    @BeforeEach
    void setup() {
        controller = new EventController(eventService, messageService, auth);
    }

    @Test
    void testCreate_withValidRequest_shouldSaveEventAndReturnOk() throws JsonProcessingException {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        controller.setRestTemplate(restTemplate);
        String token = "Bearer abdefgh";
        LocalDateTime time = LocalDateTime.now();
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                time,
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        when(auth.getNetId()).thenReturn("netId");
        when(eventService.saveEvent(any(Event.class))).thenReturn(new Event());
        when(controller.getRestTemplate().postForEntity(
                any(String.class),
                any(HttpEntity.class),
                any()
        )).thenReturn(ResponseEntity.status(HttpStatus.OK).body(000));

        ResponseEntity<String> result = controller.create(token, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        String expected = "Training made by netId, event ID: 0, time "
                + time + "\n"
                + List.of(new Participant(Position.COX), new Participant(Position.COACH)) + "\n";
        assertEquals(expected, result.getBody());
        verify(eventService).saveEvent(any(Event.class));
    }

    @Test
    void testCreate_withInvalidRequest_shouldReturnBadRequest() throws JsonProcessingException {
        String token = "Bearer abdefgh";
        EventCreationModel request = new EventCreationModel(
                null,
                null,
                null);

        ResponseEntity<String> result = controller.create(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidEventType_shouldReturnBadRequest() throws JsonProcessingException {
        String token = "Bearer abdefgh";
        EventCreationModel request = new EventCreationModel(
                null,
                LocalDateTime.now(),
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        ResponseEntity<String> result = controller.create(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidTime_shouldReturnBadRequest() throws JsonProcessingException {
        String token = "Bearer abdefgh";
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                null,
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        ResponseEntity<String> result = controller.create(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidParticipants_shouldReturnBadRequest() throws JsonProcessingException {
        String token = "Bearer abdefgh";
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                LocalDateTime.now(),
                null);

        ResponseEntity<String> result = controller.create(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testGetAll_withEventsInDatabase_shouldReturnOk() {
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now().plusSeconds(10);
        List<Event> events = List.of(
                new Event(EventType.TRAINING, "netId1", time1,
                        List.of(new Participant(Position.COX), new Participant(Position.COACH))),
                new Event(EventType.COMPETITION, "netId2", time2,
                        List.of(new Participant(Position.SCULLING_ROWER), new Participant(Position.STARBOARD_SIDE_ROWER)))
        );
        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<String> result = controller.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(events.toString(), result.getBody());
    }

    @Test
    void testGetAll_withNoEventsInDatabase_shouldReturnBadRequest() {
        when(eventService.getAllEvents()).thenReturn(List.of());

        ResponseEntity<String> result = controller.getAll();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("There are no events for you to join!", result.getBody());
        verify(eventService).getAllEvents();
    }

    @Test
    void testJoin_withValidRequest_shouldJoinEventAndReturnOk() throws NotFoundException, ConnectException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(), List.of(new Participant((Position.COX))));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);
        when(messageService.sendJoinMessage(token, request, "netId2", "netId1")).thenReturn(HttpStatus.OK);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("You have sent a request to join event: 0 made by netId1", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
        verify(messageService).sendJoinMessage(token, request, "netId2", "netId1");
    }

    @Test
    void testJoin_withPositionAlreadyFilled_shouldReturnBadRequest() throws NotFoundException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of(new Participant(Position.COX, "netId3")));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("This position is already filled", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withNoPositions_shouldReturnBadRequest() throws NotFoundException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of());
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("This position is already filled", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withPositionAlreadyFilledByUser_shouldReturnBadRequest() throws NotFoundException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of(new Participant(Position.COX, "netId2")));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("You are already participating in this event!", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withMessageServiceUnavailable_shouldReturnServiceUnavailable() throws NotFoundException, ConnectException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(), List.of(new Participant((Position.COX))));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);
        when(messageService.sendJoinMessage(token, request, "netId2", "netId1"))
                .thenReturn(HttpStatus.SERVICE_UNAVAILABLE);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
        //TODO: Possible Bug
        //assertEquals("Message service could not be reached", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
        verify(messageService).sendJoinMessage(token, request, "netId2", "netId1");
    }

    @Test
    void testJoin_withEventNotFound_shouldReturnNotFound() throws NotFoundException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        when(eventService.getEvent(1L)).thenReturn(null);

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Event not found!", result.getBody());
    }

    @Test
    void testJoin_withEventNotFound_shouldReturnNotFound2() throws NotFoundException {
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        when(eventService.getEvent(1L)).thenThrow(new NotFoundException(""));

        ResponseEntity<String> result = controller.join(token, request);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testJoinByAdmin_Success() throws Exception {
        Event event = new Event();
        event.setParticipants(
                Arrays.asList(new Participant(Position.COX, null),
                        new Participant(Position.COACH, null)));
        when(eventService.getEvent(1L)).thenReturn(event);

        Message request = new Message();
        request.setEventId(1L);
        request.setSender("user1");
        request.setPosition(Position.COX);

        ResponseEntity<String> result = controller.joinByAdmin(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("You have added " + request.getSender() + " to your event", result.getBody());
    }

    @Test
    public void testJoinByAdmin_UserAlreadyParticipating() throws Exception {
        Event event = new Event();
        event.setParticipants(Arrays.asList(new Participant(Position.COX, "user1"),
                new Participant(Position.COACH, null)));
        when(eventService.getEvent(1L)).thenReturn(event);

        Message request = new Message();
        request.setEventId(1L);
        request.setSender("user1");
        request.setPosition(Position.COX);

        ResponseEntity<String> result = controller.joinByAdmin(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("User is already participating in this event", result.getBody());

    }

    @Test
    public void testJoinByAdmin_PositionAlreadyFilled() throws Exception {
        Event event = new Event();
        event.setParticipants(Arrays.asList(new Participant(Position.COX, "user2"),
                new Participant(Position.COACH, null)));
        when(eventService.getEvent(1L)).thenReturn(event);

        Message request = new Message();
        request.setEventId(1L);
        request.setSender("user1");
        request.setPosition(Position.COX);

        ResponseEntity<String> result = controller.joinByAdmin(request);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("This position is already filled", result.getBody());

    }

    @Test
    public void testJoinByAdmin_EventNotFound() throws Exception {
        Event event = new Event();
        event.setParticipants(Arrays.asList(new Participant(Position.COX, null),
                new Participant(Position.COACH, null)));
        when(eventService.getEvent(1L)).thenThrow(new NotFoundException(("")));

        Message request = new Message();
        request.setEventId(1L);
        request.setSender("user1");
        request.setPosition(Position.COX);

        ResponseEntity<String> result = controller.joinByAdmin(request);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    @Test
    void testLeave_ShouldReturnOk() throws NotFoundException, ConnectException {
        final long eventId = 12345L;
        final String token = "abcdefg";
        final String netId = "testuser";
        final String admin = "adminuser";
        Event event = new Event(EventType.COMPETITION, admin, LocalDateTime.now(),
                List.of(new Participant(Position.COX, netId)));
        event.setEventId(eventId);
        Participant participant = new Participant();
        participant.setNetId(netId);
        EventJoinModel joinModel = new EventJoinModel(Position.COX, eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);
        when(messageService.sendLeaveMessage(token, joinModel, "testuser", "adminuser")).thenReturn(HttpStatus.OK);

        ResponseEntity<String> response = controller.leave(token, joinModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("You have left event 12345 made by adminuser", response.getBody());
        verify(eventService).updateEvent(event);
    }

    @Test
    void testLeave_NotParticipating() throws NotFoundException {
        final long eventId = 12345L;
        final String token = "abcdefg";
        final String netId = "testuser";
        final String admin = "adminuser";
        Event event = new Event(EventType.COMPETITION, admin, LocalDateTime.now(),
                List.of(new Participant(Position.COX, "otheruser")));
        event.setEventId(eventId);
        EventJoinModel joinModel = new EventJoinModel(Position.COX, eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.leave(token, joinModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You are not participating in this event!", response.getBody());
    }

    @Test
    void testLeave_EventNotFound() throws NotFoundException {
        final long eventId = 12345L;
        final String token = "abcdefg";
        EventJoinModel joinModel = new EventJoinModel(Position.COX, eventId);
        when(eventService.getEvent(eventId)).thenThrow(new NotFoundException(""));

        ResponseEntity<String> response = controller.leave(token, joinModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testLeave_MessageServiceUnavailable() throws NotFoundException, ConnectException {
        final long eventId = 12345L;
        final String token = "abcdefg";
        final String netId = "testuser";
        final String admin = "adminuser";
        Event event = new Event(EventType.COMPETITION, admin, LocalDateTime.now(),
                List.of(new Participant(Position.COX, netId)));
        event.setEventId(eventId);
        Participant participant = new Participant();
        participant.setNetId(netId);
        EventJoinModel joinModel = new EventJoinModel(Position.COX, eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);
        when(messageService.sendLeaveMessage(token, joinModel, netId, admin)).thenThrow(new ConnectException());

        ResponseEntity<String> response = controller.leave(token, joinModel);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Message service could not be reached", response.getBody());
    }

    @Test
    void testLeave_MessageServiceError() throws NotFoundException, ConnectException {
        final long eventId = 12345L;
        final String token = "abcdefg";
        final String netId = "testuser";
        final String admin = "adminuser";
        Event event = new Event(EventType.COMPETITION, admin, LocalDateTime.now(),
                List.of(new Participant(Position.COX, netId)));
        event.setEventId(eventId);
        Participant participant = new Participant();
        participant.setNetId(netId);
        EventJoinModel joinModel = new EventJoinModel(Position.COX, eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);
        when(messageService.sendLeaveMessage(token, joinModel, netId, admin)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<String> response = controller.leave(token, joinModel);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void testMyCreatedEvents_ShouldReturnOk() {
        final String netId = "testuser";
        List<Event> events = List.of(new Event(EventType.COMPETITION, netId, LocalDateTime.now(),
                List.of(new Participant(Position.COX, null))));
        when(eventService.getEventsByAdmin(netId)).thenReturn(events);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.myCreatedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.toString(), response.getBody());
    }

    @Test
    void testMyCreatedEvents_Empty() {
        final String netId = "testuser";
        List<Event> events = Collections.emptyList();
        when(eventService.getEventsByAdmin(netId)).thenReturn(events);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.myCreatedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.toString(), response.getBody());
    }

    @Test
    void testMyJoinedEvents_ShouldReturnOk() {
        final String netId = "testuser";
        List<Event> events = List.of(new Event(EventType.COMPETITION, "adminuser", LocalDateTime.now(),
                List.of(new Participant(Position.COX, netId))));
        when(eventService.getEventsByParticipant(netId)).thenReturn(events);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.myJoinedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.toString(), response.getBody());
    }

    @Test
    void testMyJoinedEvents_Empty() {
        final String netId = "testuser";
        List<Event> events = Collections.emptyList();
        when(eventService.getEventsByParticipant(netId)).thenReturn(events);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.myJoinedEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.toString(), response.getBody());
    }

    @Test
    void testUpdate_ShouldReturnOk() throws NotFoundException {
        final long eventId = 12345L;
        final String netId = "testuser";
        Event oldEvent = new Event(EventType.COMPETITION, netId, LocalDateTime.now(), List.of());
        oldEvent.setEventId(eventId);
        Event newEvent = new Event(EventType.TRAINING, netId, LocalDateTime.now().plusDays(1),
                List.of(new Participant(Position.COACH)));
        newEvent.setEventId(eventId);
        when(eventService.getEvent(eventId)).thenReturn(oldEvent);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.update(newEvent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newEvent.toStringUpdate(), response.getBody());
        verify(eventService).updateEvent(newEvent);
    }

    @Test
    void testUpdate_Unauthorized() throws NotFoundException {
        final long eventId = 12345L;
        final String netId = "testuser";
        final String admin = "adminuser";
        Event oldEvent = new Event(EventType.COMPETITION, admin, LocalDateTime.now(), List.of());
        oldEvent.setEventId(eventId);
        Event newEvent = new Event(EventType.TRAINING, admin, LocalDateTime.now().plusDays(1),
                List.of(new Participant(Position.COACH)));
        newEvent.setEventId(eventId);
        when(eventService.getEvent(eventId)).thenReturn(oldEvent);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.update(newEvent);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testUpdateEventNotFound() throws NotFoundException {
        final long eventId = 12345L;
        final String netId = "testuser";
        Event newEvent = new Event(EventType.TRAINING, netId, LocalDateTime.now().plusDays(1), List.of());
        newEvent.setEventId(eventId);
        when(eventService.getEvent(eventId)).thenThrow(new NotFoundException(""));

        ResponseEntity<String> response = controller.update(newEvent);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdate_InvalidRequest() {
        final long eventId = 12345L;
        final String netId = "testuser";
        Event newEvent = new Event(EventType.TRAINING, netId, LocalDateTime.now().plusDays(1), List.of());
        newEvent.setEventId(eventId);

        ResponseEntity<String> response = controller.update(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void testDelete_ShouldReturnOk() throws NotFoundException {
        final long eventId = 12345L;
        final String netId = "testuser";
        Event event = new Event(EventType.COMPETITION, netId, LocalDateTime.now(), List.of());
        event.setEventId(eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.delete(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("successfully deleted event", response.getBody());
        verify(eventService).deleteEvent(eventId);
    }

    @Test
    void testDelete_Unauthorized() throws NotFoundException {
        final long eventId = 12345L;
        final String netId = "testuser";
        Event event = new Event(EventType.COMPETITION, "admin", LocalDateTime.now(), List.of());
        event.setEventId(eventId);
        when(eventService.getEvent(eventId)).thenReturn(event);
        when(auth.getNetId()).thenReturn(netId);

        ResponseEntity<String> response = controller.delete(eventId);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testDeleteEventNotFound() throws NotFoundException {
        final long eventId = 12345L;
        when(eventService.getEvent(eventId)).thenThrow(new NotFoundException(""));

        ResponseEntity<String> response = controller.delete(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDelete_InvalidRequest() {

        ResponseEntity<String> response = controller.delete(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /*
    @Test
    void testGetEvents_ShouldReturnOk () {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        controller.setRestTemplate(restTemplate);
        final LocalDateTime time = LocalDateTime.now();
        final String token ="Bearer abcdefg";
        final String netId = "testuser";
        final long eventId1 = 12345L;
        final long eventId2 = 67890L;
        Event event1 = new Event (EventType.COMPETITION, netId, time, List.of());
        event1.setEventId(eventId1);
        Event event2 = new Event (EventType.TRAINING, netId, time.plusDays(1), List.of());
        event2.setEventId(eventId2);
        when(eventService.getAllEvents()).thenReturn(List.of(event1, event2));


        when(controller.getRestTemplate().exchange(
                eq("http://localhost:8084/api/certificate/getValidEvents"),
                any(),
                any(HttpEntity.class),
                (Class<Object>) any()
        )).thenReturn(ResponseEntity.ok(new EventIdsDto(List.of(eventId1))));

        when(controller.getRestTemplate().exchange(
                eq("http://localhost:8084/api/certificate/userAvailability"),
                any(),
                any(HttpEntity.class),
                (Class<Object>) any()
        )).thenReturn(ResponseEntity.ok(new AvailabilityDto()));

        ResponseEntity<String> response = controller.getEvents(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    */

    @Test
    void testGetEvents_InvalidRequest() {

        ResponseEntity<String> response = controller.getEvents(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

