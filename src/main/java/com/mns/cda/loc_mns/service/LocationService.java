package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

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
    public List<Location> getAllLocations() {
        return locationDao.findAll();
    }

    /**
     * Récupère une location à partir de son identifiant.
     *
     * @param id identifiant unique de la location recherchée
     * @return une réponse HTTP contenant la location si elle existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucune location ne correspond à cet identifiant
     */
    public ResponseEntity<Location> getLocation(int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);
        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLocation.get(), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle location en base de données.
     *
     * @param newLocation données de la location à créer
     * @return une réponse HTTP contenant la location créée (201 CREATED)
     */
    public ResponseEntity<Location> createLocation(Location newLocation) {
        newLocation.setId(null);
        locationDao.save(newLocation);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    /**
     * Supprime une location à partir de son identifiant.
     *
     * @param id identifiant unique de la location à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucune location ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteLocation(int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        locationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour une location existante en remplaçant ses données.
     *
     * @param id identifiant unique de la location à mettre à jour
     * @param locationToUpdate nouvelles données de la location
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucune location ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateLocation(int id, Location locationToUpdate) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        locationToUpdate.setId(id);
        locationDao.save(locationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
