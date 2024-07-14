package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {

    List<TableEntity> findAllByisAvailableIsTrue();
}
