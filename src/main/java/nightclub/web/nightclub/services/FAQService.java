package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.FAQ;

import java.util.Optional;
import java.util.Set;

public interface FAQService {

    Set<FAQ> getAllFAQs();

    Optional<FAQ> getFAQById(Long id);

    void saveFAQ(FAQ faq);

    boolean isEmpty();
}
