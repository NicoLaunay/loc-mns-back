package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Autowired
    protected LocationDao locationDao;

    /**
     * Récupère l'ensemble des locations enregistrées en base de données.
     *
     * @return une liste non nulle de locations, éventuellement vide si aucune donnée n'est présente
     */
    public List<Location> getAll() {
        return locationDao.findAll();
    }

    /**
     * Récupère une location à partir de son identifiant.
     *
     * @param id identifiant unique de la location recherchée
     * @return la location correspondante
     * @throws IdNotFoundException si aucune location ne correspond à cet identifiant
     */
    public Location get(int id) throws IdNotFoundException {
        return locationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune location ne correspond à cet identifiant"));
    }

    /**
     * Crée une nouvelle location en base de données.
     *
     * @param newLocation données de la location à créer
     * @return la location créée
     */
    public Location create(Location newLocation) {
        newLocation.setId(null);
        return locationDao.save(newLocation);
    }

    /**
     * Supprime une location à partir de son identifiant.
     *
     * @param id identifiant unique de la location à supprimer
     * @throws IdNotFoundException si aucune location ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        locationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune location ne correspond à cet identifiant"));
        locationDao.deleteById(id);
    }

    /**
     * Met à jour une location existante en remplaçant ses données.
     *
     * @param id identifiant unique de la location à mettre à jour
     * @param locationToUpdate nouvelles données de la location
     * @throws IdNotFoundException si aucune location ne correspond à cet identifiant
     */
    public void update(int id, Location locationToUpdate) throws IdNotFoundException {
        locationDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucune location ne correspond à cet identifiant"));
        locationToUpdate.setId(id);
        locationDao.save(locationToUpdate);
    }
}
