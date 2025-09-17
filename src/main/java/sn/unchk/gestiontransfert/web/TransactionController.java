package sn.unchk.gestiontransfert.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sn.unchk.gestiontransfert.model.UtilisateurModel;
import sn.unchk.gestiontransfert.service.TransactionService;
import sn.unchk.gestiontransfert.service.dto.request.TransferRequestDto;
import sn.unchk.gestiontransfert.service.impl.RecuPdfService;
import sn.unchk.gestiontransfert.web.response.PageResponse;
import sn.unchk.gestiontransfert.service.dto.response.TransactionDto;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final RecuPdfService recuPdfService;

    public TransactionController(TransactionService transactionService, RecuPdfService recuPdfService) {
        this.transactionService = transactionService;
        this.recuPdfService = recuPdfService;
    }

    @PostMapping("/transfert")
    public TransactionDto transfert(
            @AuthenticationPrincipal UtilisateurModel expediteur,
            @RequestBody TransferRequestDto request
    ) {
        return transactionService.effectuerTransfert(expediteur, request);
    }

    //  Lister toutes les transactions (paginées + tri par updatedAt desc)
    @GetMapping
    public PageResponse<TransactionDto> getAllTransactions(Pageable pageable) {
        Page<TransactionDto> page = transactionService.getAllTransactions(pageable);
        return PageResponse.from(page);
    }

    //  Transactions de l’utilisateur connecté
    @GetMapping("/me")
    public PageResponse<TransactionDto> getMyTransactions(
            @AuthenticationPrincipal UtilisateurModel utilisateur,
            Pageable pageable
    ) {
        Page<TransactionDto> page = transactionService.getTransactionsByUtilisateur(utilisateur, pageable);
        return PageResponse.from(page);
    }

    //  Transactions d’un utilisateur donné
    @GetMapping("/utilisateur/{telephone}")
    public PageResponse<TransactionDto> getTransactionsByTelephone(
            @PathVariable String telephone,
            Pageable pageable
    ) {
        Page<TransactionDto> page = transactionService.getTransactionsByTelephone(telephone, pageable);
        return PageResponse.from(page);
    }

    //  Détails transaction
    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    //  Dépôt
    @PostMapping("/depot")
    public TransactionDto depot(
            @AuthenticationPrincipal UtilisateurModel utilisateur,
            @RequestParam BigDecimal montant
    ) {
        return transactionService.effectuerDepot(utilisateur, montant);
    }

    //  Retrait
    @PostMapping("/retrait")
    public TransactionDto retrait(
            @AuthenticationPrincipal UtilisateurModel utilisateur,
            @RequestParam BigDecimal montant
    ) {
        return transactionService.effectuerRetrait(utilisateur, montant);
    }

//    // API PDF : télécharger le reçu
//    @GetMapping("/{id}/recu")
//    public ResponseEntity<byte[]> downloadRecu(@PathVariable Long id) {
//        TransactionDto transaction = transactionService.getTransactionById(id);
//        if (transaction == null) {
//            throw new RuntimeException("Transaction introuvable");
//        }
//
//        byte[] pdfBytes = recuPdfService.genererRecu(transaction);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData(
//                "attachment",
//                "recu_transaction_" + transaction.getId() + ".pdf"
//        );
//
//        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//    }

    @GetMapping("/{id}/recu")
    public ResponseEntity<byte[]> downloadRecu(@PathVariable Long id) {
        TransactionDto transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            throw new RuntimeException("Transaction introuvable");
        }

        byte[] pdfBytes = recuPdfService.genererRecu(transaction);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename("recu_transaction_" + transaction.getId() + ".pdf")
                        .build()
        );

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }



}
