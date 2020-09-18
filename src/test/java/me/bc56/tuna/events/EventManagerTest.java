package me.bc56.tuna.events;

import me.bc56.tuna.ThreadManager;
import me.bc56.tuna.events.type.Event;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventManagerTest {

    static ThreadManager threadManager;

    static EventManager eventManager;
    @Mock EventReceiver mockEventReceiver;

    @BeforeAll
    static void setup() {
        threadManager = new ThreadManager();

        eventManager = EventManager.getInstance();
        threadManager.runCoreModule(eventManager);
    }

    @Test
    void testValidEventConsumption() {
        UUID fakeProducerId = UUID.randomUUID();
        String fakeEventType = "Hi, Dad."; //TODO: Should I randomly generate the type string?
        Event testEvent = new Event(fakeProducerId, fakeEventType);

        //TODO: Should I test permutations of these options?
        EventFilter eventFilter = new EventFilter();
        eventFilter.addEventSource(fakeProducerId);
        eventFilter.addEventType(fakeEventType);

        eventManager.registerReceiver(mockEventReceiver, eventFilter);

        threadManager.execute(() -> eventManager.submitEvent(testEvent));

        verify(mockEventReceiver, timeout(100)).enqueue(testEvent);
    }

    @Test
    void testInvalidEventConsumption() {
        UUID fakeProducerId = UUID.randomUUID();
        String fakeEventType = "Hi, Dad."; //TODO: Should I randomly generate the type string?
        String otherFakeEventType = "Hi, Mom.";
        Event testEvent = new Event(fakeProducerId, otherFakeEventType);

        //TODO: Should I test permutations of these options?
        EventFilter eventFilter = new EventFilter();
        eventFilter.addEventSource(fakeProducerId);
        eventFilter.addEventType(fakeEventType);

        eventManager.registerReceiver(mockEventReceiver, eventFilter);

        threadManager.execute(() -> eventManager.submitEvent(testEvent));

        verify(mockEventReceiver, timeout(100).times(0)).enqueue(testEvent);
    }

    @AfterAll
    static void teardown() {
        threadManager.stopCoreModule(eventManager);
    }
}