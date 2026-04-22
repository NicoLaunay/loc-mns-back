package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.StateDao;
import com.mns.cda.loc_mns.model.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateService {

    @Autowired
    protected StateDao stateDao;

    /**
     * Récupère l'ensemble des états enregistrés en base de données.
     *
     * @return une liste non nulle d'états, éventuellement vide si aucune donnée n'est présente
     */
    public List<State> getAllStates() {
        return stateDao.findAll();
    }

    /**
     * Récupère un état à partir de son identifiant.
     *
     * @param id identifiant unique de l'état recherché
     * @return une réponse HTTP contenant l'état si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun état ne correspond à cet identifiant
     */
    public ResponseEntity<State> getState(int id) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalState.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouvel état en base de données.
     *
     * @param newState données de l'état à créer
     * @return une réponse HTTP contenant l'état créé (201 CREATED)
     */
    public ResponseEntity<State> createState(State newState) {
        newState.setId(null);
        stateDao.save(newState);
        return new ResponseEntity<>(newState, HttpStatus.CREATED);
    }

    /**
     * Supprime un état à partir de son identifiant.
     *
     * @param id identifiant unique de l'état à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun état ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteState(int id) {
        Optional<State> optionalState = stateDao.findById(id);

        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stateDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un état existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'état à mettre à jour
     * @param stateToUpdate nouvelles données de l'état
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun état ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateState(int id, State stateToUpdate) {
        Optional<State> optionalState = stateDao.findById(id);

        if (optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        stateToUpdate.setId(id);
        stateDao.save(stateToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
