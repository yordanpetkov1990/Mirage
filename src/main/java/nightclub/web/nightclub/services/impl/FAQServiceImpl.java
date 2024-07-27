package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.FAQ;
import nightclub.web.nightclub.repository.FAQRepository;
import nightclub.web.nightclub.services.FAQService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    public Set<FAQ> getAllFAQs() {
        return new HashSet<>(faqRepository.findAll());
    }

    @Override
    public Optional<FAQ> getFAQById(Long id) {
        return this.faqRepository.findById(id);
    }

    @Override
    public void saveFAQ(FAQ faq) {
        this.faqRepository.save(faq);
    }

    @Override
    public boolean isEmpty() {
        return this.faqRepository.count() == 0;
    }
}
