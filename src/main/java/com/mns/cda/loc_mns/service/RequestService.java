package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @return une réponse HTTP contenant la demande si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune demande ne correspond à cet identifiant
     */
    public ResponseEntity<Request> getRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);
        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalRequest.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle demande en base de données.
     *
     * @param newRequest données de la demande à créer
     * @return une réponse HTTP contenant la demande créée (201 CREATED)
     */
    public ResponseEntity<Request> createRequest(Request newRequest) {
        newRequest.setId(null);
        requestDao.save(newRequest);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    /**
     * Supprime une demande à partir de son identifiant.
     *
     * @param id identifiant unique de la demande à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune demande ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);

        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        requestDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une demande existante en remplaçant ses données.
     *
     * @param id identifiant unique de la demande à mettre à jour
     * @param requestToUpdate nouvelles données de la demande
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune demande ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateRequest(int id, Request requestToUpdate) {
        Optional<Request> optionalRequest = requestDao.findById(id);

        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        requestToUpdate.setId(id);
        requestDao.save(requestToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
