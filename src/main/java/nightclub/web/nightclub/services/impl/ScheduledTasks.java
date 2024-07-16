package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.services.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ReservationService reservationService;

    public ScheduledTasks(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 */2 * * * *")
    public void removeReservationsFromTheDB(){
        this.reservationService.removeAllOlderThanNow();
    }
}
