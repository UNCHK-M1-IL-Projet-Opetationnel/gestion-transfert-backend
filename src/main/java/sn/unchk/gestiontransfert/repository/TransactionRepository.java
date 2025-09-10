package sn.unchk.gestiontransfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.unchk.gestiontransfert.model.TransactionModel;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
}
