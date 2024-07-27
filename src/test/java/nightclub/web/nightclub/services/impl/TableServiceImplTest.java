package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.TableEntity;
import nightclub.web.nightclub.repository.TableRepository;
import nightclub.web.nightclub.services.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TableServiceImplTest {

    @InjectMocks
    private TableServiceImpl tableService;

    @Mock
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        TableEntity table1 = new TableEntity();
        TableEntity table2 = new TableEntity();

        List<TableEntity> tables = List.of(table1, table2);
        when(tableRepository.findAll()).thenReturn(tables);

        List<TableEntity> result = tableService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(table1));
        assertTrue(result.contains(table2));
    }

    @Test
    void testFindByIdFound() {
        TableEntity table = new TableEntity();
        Long tableId = 1L;

        when(tableRepository.findById(tableId)).thenReturn(Optional.of(table));

        Optional<TableEntity> result = tableService.findById(tableId);

        assertTrue(result.isPresent());
        assertEquals(table, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        Long tableId = 1L;

        when(tableRepository.findById(tableId)).thenReturn(Optional.empty());

        Optional<TableEntity> result = tableService.findById(tableId);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllFreeTables() {
        TableEntity table1 = new TableEntity();
        TableEntity table2 = new TableEntity();

        List<TableEntity> freeTables = List.of(table1, table2);
        Reservation reservation = mock(Reservation.class);
        when(reservation.getEvent()).thenReturn(mockEvent());

        when(tableRepository.findAvailableTablesForEvent(reservation.getEvent().getId())).thenReturn(freeTables);

        List<TableEntity> result = tableService.findAllFreeTables(reservation);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(table1));
        assertTrue(result.contains(table2));
    }

    private Event mockEvent() {
        Event event = new Event();
        event.setId(1L);
        return event;
    }
}
