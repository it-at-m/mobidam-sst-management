package de.muenchen.mobidam.repository;

import de.muenchen.mobidam.domain.Datenstransfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface DatentransferRepository extends CrudRepository<Datenstransfer, UUID> {

    List<Datenstransfer> findDatenstransfersBySchnittstelleId(UUID id);
}
