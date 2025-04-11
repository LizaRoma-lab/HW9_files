package guru.qa;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.Event;
import guru.qa.model.EventInner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class FilesParsingTest {


    @Test
    void jsonFileParsingTest() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("event.json")) {
            assertNotNull(is, "Файл event.json не найден! ");

            ObjectMapper objectMapper = new ObjectMapper();
            Event event = objectMapper.readValue(is, Event.class);

            assertEquals(21494285, event.getId());
            assertEquals("live", event.getNs());
            assertEquals("Tennis", event.getTitle());

            EventInner ids = event.getIds();
            assertEquals(34, ids.getGameId());
            assertEquals(398, ids.getCategoryId());
            assertEquals(4450, ids.getTournamentId());


        }

    }
}


