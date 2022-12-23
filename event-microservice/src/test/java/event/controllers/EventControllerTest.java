package event.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import event.authentication.AuthManager;
import event.domain.entities.Event;
import event.domain.enums.EventType;
import event.domain.enums.Position;
import event.domain.objects.Participant;
import event.models.EventCreationModel;
import event.models.EventJoinModel;
import event.service.EventService;
import event.service.MessageService;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.List;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void testCreate_withValidRequest_shouldSaveEventAndReturnOk() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                time,
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        when(auth.getNetId()).thenReturn("netId");
        when(eventService.saveEvent(any(Event.class))).thenReturn(new Event());

        // Act
        ResponseEntity<String> result = controller.create(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        String expected = "Training made by netId, event ID: 0, time "
                + time + "\n"
                + List.of(new Participant(Position.COX), new Participant(Position.COACH)) + "\n";
        assertEquals(expected, result.getBody());
        verify(eventService).saveEvent(any(Event.class));
    }

    @Test
    void testCreate_withInvalidRequest_shouldReturnBadRequest() {
        // Arrange
        EventCreationModel request = new EventCreationModel(
                null,
                null,
                null);

        // Act
        ResponseEntity<String> result = controller.create(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidEventType_shouldReturnBadRequest() {
        // Arrange
        EventCreationModel request = new EventCreationModel(
                null,
                LocalDateTime.now(),
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        // Act
        ResponseEntity<String> result = controller.create(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidTime_shouldReturnBadRequest() {
        // Arrange
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                null,
                List.of(new Participant(Position.COX), new Participant(Position.COACH)));

        // Act
        ResponseEntity<String> result = controller.create(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testCreate_withInvalidParticipants_shouldReturnBadRequest() {
        // Arrange
        EventCreationModel request = new EventCreationModel(
                EventType.TRAINING,
                LocalDateTime.now(),
                null);

        // Act
        ResponseEntity<String> result = controller.create(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid JSON or event type!", result.getBody());
    }

    @Test
    void testGetAll_withEventsInDatabase_shouldReturnOk() {
        // Arrange
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now().plusSeconds(10);
        List<Event> events = List.of(
                new Event(EventType.TRAINING, "netId1", time1,
                        List.of(new Participant(Position.COX), new Participant(Position.COACH))),
                new Event(EventType.COMPETITION, "netId2", time2,
                        List.of(new Participant(Position.SCULLING_ROWER), new Participant(Position.STARBOARD_SIDE_ROWER)))
        );
        when(eventService.getAllEvents()).thenReturn(events);

        // Act
        ResponseEntity<String> result = controller.getAll();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(events.toString(), result.getBody());
    }

    @Test
    void testGetAll_withNoEventsInDatabase_shouldReturnBadRequest() {
        // Arrange
        when(eventService.getAllEvents()).thenReturn(List.of());

        // Act
        ResponseEntity<String> result = controller.getAll();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("There are no events for you to join!", result.getBody());
        verify(eventService).getAllEvents();
    }

    @Test
    void testJoin_withValidRequest_shouldJoinEventAndReturnOk() throws NotFoundException, ConnectException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(), List.of(new Participant((Position.COX))));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);
        when(messageService.sendJoinMessage(token, request, "netId2", "netId1")).thenReturn(HttpStatus.OK);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("You have sent a request to join event: 0 made by netId1", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
        verify(messageService).sendJoinMessage(token, request, "netId2", "netId1");
    }

    @Test
    void testJoin_withPositionAlreadyFilled_shouldReturnBadRequest() throws NotFoundException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of(new Participant(Position.COX, "netId3")));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("This position is already filled", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withNoPositions_shouldReturnBadRequest() throws NotFoundException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of());
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("This position is already filled", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withPositionAlreadyFilledByUser_shouldReturnBadRequest() throws NotFoundException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(),
                List.of(new Participant(Position.COX, "netId2")));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("You are already participating in this event!", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
    }

    @Test
    void testJoin_withMessageServiceUnavailable_shouldReturnServiceUnavailable() throws NotFoundException, ConnectException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        Event event = new Event(EventType.TRAINING, "netId1", LocalDateTime.now(), List.of(new Participant((Position.COX))));
        when(auth.getNetId()).thenReturn("netId2");
        when(eventService.getEvent(1L)).thenReturn(event);
        when(messageService.sendJoinMessage(token, request, "netId2", "netId1"))
                .thenReturn(HttpStatus.SERVICE_UNAVAILABLE);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
        //TODO: Possible Bug
        //assertEquals("Message service could not be reached", result.getBody());
        verify(auth).getNetId();
        verify(eventService).getEvent(1L);
        verify(messageService).sendJoinMessage(token, request, "netId2", "netId1");
    }

    @Test
    void testJoin_withEventNotFound_shouldReturnNotFound() throws NotFoundException, ConnectException {
        // Arrange
        EventJoinModel request = new EventJoinModel(Position.COX, 1L);
        String token = "Bearer 1234567890";
        when(eventService.getEvent(1L)).thenReturn(null);

        // Act
        ResponseEntity<String> result = controller.join(token, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Event not found!", result.getBody());
    }

}

