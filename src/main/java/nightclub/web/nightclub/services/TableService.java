package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Reservation;
import nightclub.web.nightclub.entities.TableEntity;

import java.util.List;
import java.util.Optional;

public interface TableService {

    List<TableEntity> findAll();

    Optional<TableEntity> findById(Long tableId);

    List<TableEntity> findAllFreeTables(Reservation reservation);
}
