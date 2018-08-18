package it.elia.paginated.service;

import it.elia.paginated.entity.Something;
import it.elia.paginated.repository.SomethingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SomethingService {
    @Autowired
    private SomethingRepository repository;

    @Transactional(readOnly = true)
    public Page<Something> findAll(int page, int size){
        return repository.findAll(new PageRequest(page, size, new Sort("id")));
    }


}
