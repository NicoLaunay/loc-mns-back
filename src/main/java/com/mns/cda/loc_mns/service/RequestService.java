package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    @Autowired
    protected RequestDao requestDao;

    /**
     * Récupère l'ensemble des demandes enregistrées en base de données.
     *
     * @return une liste non nulle de demandes, éventuellement vide si aucune donnée n'est présente
     */
    public List<Request> getAllRequests() {
        return requestDao.findAll();
    }

    /**
     * Récupère une demande à partir de son identifiant.
     *
     * @param id identifiant unique de la demande recherchée
     * @return la demande correspondante
     * @throws IllegalArgumentException si aucune demande ne correspond à cet identifiant
     */
    public Request getRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Aucune demande ne correspond à cet identifiant");
        }
        return optionalRequest.get();
    }

    /**
     * Crée une nouvelle demande en base de données.
     *
     * @param newRequest données de la demande à créer
     * @return la demande créée
     */
    public Request createRequest(Request newRequest) {
        newRequest.setId(null);
        return requestDao.save(newRequest);
    }

    /**
     * Supprime une demande à partir de son identifiant.
     *
     * @param id identifiant unique de la demande à supprimer
     * @throws IllegalArgumentException si aucune demande ne correspond à cet identifiant
     */
    public void deleteRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Aucune demande ne correspond à cet identifiant");
        }
        requestDao.deleteById(id);
    }

    /**
     * Met à jour une demande existante en remplaçant ses données.
     *
     * @param id identifiant unique de la demande à mettre à jour
     * @param requestToUpdate nouvelles données de la demande
     * @throws IllegalArgumentException si aucune demande ne correspond à cet identifiant
     */
    public void updateRequest(int id, Request requestToUpdate) {
        Optional<Request> optionalRequest = requestDao.findById(id);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Aucune demande ne correspond à cet identifiant");
        }
        requestToUpdate.setId(id);
        requestDao.save(requestToUpdate);
    }
}
