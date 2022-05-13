package com.acme.bnb.repositories;

import com.acme.bnb.model.Invoice;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{

    public Optional<Invoice> findByRequestId(Long requestId);
}
