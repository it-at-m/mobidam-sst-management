package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Datentransfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DatentransferRepository extends CrudRepository<Datentransfer, UUID>, PagingAndSortingRepository<Datentransfer, UUID> {

    List<Datentransfer> findDatenstransfersBySchnittstelleId(UUID id, Pageable pageable);
}
