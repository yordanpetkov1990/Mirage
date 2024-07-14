package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.TableEntity;
import nightclub.web.nightclub.repository.TableRepository;
import nightclub.web.nightclub.services.TableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public List<TableEntity> findAll() {
        return this.tableRepository.findAll();
    }

    @Override
    public Optional<TableEntity> findById(Long tableId) {
        return tableRepository.findById(tableId);
    }

    @Override
    public List<TableEntity> findAllFreeTables() {
        return tableRepository.findAllByisAvailableIsTrue();
    }
}
