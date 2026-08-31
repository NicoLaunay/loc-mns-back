package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestDao requestDao;

    /**
     * Récupère l'ensemble des demandes enregistrées en base de données.
     *
     * @return une liste non nulle de demandes, éventuellement vide si aucune donnée n'est présente
     */
    public List<Request> getAll() {
        return requestDao.findAll();
    }

    /**
     * Récupère une demande à partir de son identifiant.
     *
     * @param id identifiant unique de la demande recherchée
     * @return la demande correspondante
     * @throws IdNotFoundException si aucune demande ne correspond à cet identifiant
     */
    public Request get(int id) throws IdNotFoundException {
        return requestDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune demande ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle demande en base de données.
     *
     * @param newRequest données de la demande à créer
     * @return la demande créée
     */
    public Request create(Request newRequest) {
        newRequest.setId(null);
        return requestDao.save(newRequest);
    }

    /**
     * Supprime une demande à partir de son identifiant.
     *
     * @param id identifiant unique de la demande à supprimer
     * @throws IdNotFoundException si aucune demande ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        requestDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune demande ne correspond à cet identifiant"));
        requestDao.deleteById(id);
    }

    /**
     * Met à jour une demande existante en remplaçant ses données.
     *
     * @param id identifiant unique de la demande à mettre à jour
     * @param requestToUpdate nouvelles données de la demande
     * @throws IdNotFoundException si aucune demande ne correspond à cet identifiant
     */
    public void update(int id, Request requestToUpdate) throws IdNotFoundException {
        requestDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune demande ne correspond à cet identifiant"));
        requestToUpdate.setId(id);
        requestDao.save(requestToUpdate);
    }
}
