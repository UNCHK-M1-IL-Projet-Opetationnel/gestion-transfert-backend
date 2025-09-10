package sn.unchk.gestiontransfert.service.impl;

import sn.unchk.gestiontransfert.repository.AbonnementRepository;
import sn.unchk.gestiontransfert.service.AbonnementService;

public class AbonnementServiceImpl implements AbonnementService {
    private AbonnementRepository abonnementRepository;

    public AbonnementServiceImpl(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

}
