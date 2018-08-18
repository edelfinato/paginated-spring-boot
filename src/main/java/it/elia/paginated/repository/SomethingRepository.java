package it.elia.paginated.repository;

import it.elia.paginated.entity.Something;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomethingRepository extends PagingAndSortingRepository<Something, Long> {
}