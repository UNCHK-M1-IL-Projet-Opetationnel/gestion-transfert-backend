package sn.unchk.gestiontransfert.service.impl;

import sn.unchk.gestiontransfert.repository.TransactionRepository;
import sn.unchk.gestiontransfert.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


}
