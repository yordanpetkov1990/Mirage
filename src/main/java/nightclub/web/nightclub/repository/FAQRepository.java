package nightclub.web.nightclub.repository;

import nightclub.web.nightclub.entities.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQ,Long> {
}
