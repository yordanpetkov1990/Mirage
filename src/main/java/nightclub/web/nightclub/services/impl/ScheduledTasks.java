package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ReservationService reservationService;
    private final EventService eventService;

    public ScheduledTasks(ReservationService reservationService, EventService eventService) {
        this.reservationService = reservationService;
        this.eventService = eventService;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void removeReservationsFromTheDB(){
        this.reservationService.removeAllOlderThanNow();
    }

    @Scheduled(cron = "0 0 0 1 1/2 *")
    public void removeEventsFromTheDatabaseOlderThan2Months(){
        this.eventService.removeAllOlderThan2Months();
    }
}
