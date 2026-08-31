package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.StateDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.State;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateDao stateDao;

    /**
     * Récupère l'ensemble des états enregistrés en base de données.
     *
     * @return une liste non nulle d'états, éventuellement vide si aucune donnée n'est présente
     */
    public List<State> getAll() {
        return stateDao.findAll();
    }

    /**
     * Récupère un état à partir de son identifiant.
     *
     * @param id identifiant unique de l'état recherché
     * @return l'état correspondant
     * @throws IdNotFoundException si aucun état ne correspond à cet identifiant
     */
    public State get(int id) throws IdNotFoundException {
        return stateDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun état ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouvel état en base de données.
     *
     * @param newState données de l'état à créer
     * @return l'état créé
     */
    public State create(State newState) {
        newState.setId(null);
        return stateDao.save(newState);
    }

    /**
     * Supprime un état à partir de son identifiant.
     *
     * @param id identifiant unique de l'état à supprimer
     * @throws IdNotFoundException si aucun état ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        stateDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun état ne correspond à cet identifiant"));
        stateDao.deleteById(id);
    }

    /**
     * Met à jour un état existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'état à mettre à jour
     * @param stateToUpdate nouvelles données de l'état
     * @throws IdNotFoundException si aucun état ne correspond à cet identifiant
     */
    public void update(int id, State stateToUpdate) throws IdNotFoundException {
        stateDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun état ne correspond à cet identifiant"));
        stateToUpdate.setId(id);
        stateDao.save(stateToUpdate);
    }
}
