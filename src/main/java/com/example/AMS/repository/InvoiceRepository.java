package com.example.AMS.repository;

import com.example.AMS.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findTop5ByOrderByInvoiceDateDesc();
}
