package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.repository.SingerRepository;
import nightclub.web.nightclub.services.SingerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SingerServiceImplTest {

    @InjectMocks
    private SingerServiceImpl singerService;

    @Mock
    private SingerRepository singerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableSingers() {
        Singer singer1 = new Singer();
        singer1.setName("Singer1");

        Singer singer2 = new Singer();
        singer2.setName("Singer2");

        List<Singer> singers = new ArrayList<>();

        singers.add(singer1);
        singers.add(singer2);

        when(singerRepository.findAll()).thenReturn(singers);

        Set<String> result = singerService.getAvailableSingers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Singer1"));
        assertTrue(result.contains("Singer2"));
    }

    @Test
    void testGetSingerByNameFound() {
        Singer singer = new Singer();
        singer.setName("Singer1");

        when(singerRepository.findByName("Singer1")).thenReturn(Optional.of(singer));

        Optional<Singer> result = singerService.getSingerByName("Singer1");

        assertTrue(result.isPresent());
        assertEquals("Singer1", result.get().getName());
    }

    @Test
    void testGetSingerByNameNotFound() {
        when(singerRepository.findByName("Singer1")).thenReturn(Optional.empty());

        Optional<Singer> result = singerService.getSingerByName("Singer1");

        assertFalse(result.isPresent());
    }
}
