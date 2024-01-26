package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Datentransfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface DatentransferRepository extends CrudRepository<Datentransfer, UUID> {

    List<Datentransfer> findDatenstransfersBySchnittstelleId(UUID id);
}
