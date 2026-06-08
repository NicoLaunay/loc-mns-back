package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.StateDao;
import com.mns.cda.loc_mns.model.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return l'état correspondant
     * @throws IllegalArgumentException si aucun état ne correspond à cet identifiant
     */
    public State getState(int id) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            throw new IllegalArgumentException("Aucun état ne correspond à cet identifiant");
        }
        return optionalState.get();
    }

    /**
     * Crée un nouvel état en base de données.
     *
     * @param newState données de l'état à créer
     * @return l'état créé
     */
    public State createState(State newState) {
        newState.setId(null);
        return stateDao.save(newState);
    }

    /**
     * Supprime un état à partir de son identifiant.
     *
     * @param id identifiant unique de l'état à supprimer
     * @throws IllegalArgumentException si aucun état ne correspond à cet identifiant
     */
    public void deleteState(int id) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            throw new IllegalArgumentException("Aucun état ne correspond à cet identifiant");
        }
        stateDao.deleteById(id);
    }

    /**
     * Met à jour un état existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'état à mettre à jour
     * @param stateToUpdate nouvelles données de l'état
     * @throws IllegalArgumentException si aucun état ne correspond à cet identifiant
     */
    public void updateState(int id, State stateToUpdate) {
        Optional<State> optionalState = stateDao.findById(id);
        if (optionalState.isEmpty()) {
            throw new IllegalArgumentException("Aucun état ne correspond à cet identifiant");
        }
        stateToUpdate.setId(id);
        stateDao.save(stateToUpdate);
    }
}
