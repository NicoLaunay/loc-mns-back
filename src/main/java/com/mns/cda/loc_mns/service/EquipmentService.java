package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.EquipmentDao;
import com.mns.cda.loc_mns.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    @Autowired
    protected EquipmentDao equipmentDao;

    /**
     * Récupère l'ensemble des équipements enregistrées en base de données.
     *
     * @return une liste non nulle d'équipements, éventuellement vide si aucune donnée n'est présente
     */
    public List<Equipment> getAll() {
        return equipmentDao.findAll();
    }

    /**
     * Récupère l'ensemble des équipements d'un modèle disponibles aux dates indiquées.
     *
     * @return une liste non nulle d'équipements, éventuellement vide si aucune donnée n'est présente
     */
    public List<Equipment> getAllOfModelAvailableOnPeriod(int modelId, Date startDate, Date endDate) {

        return equipmentDao.findAllOfModelAvailableOnPeriod(modelId, startDate, endDate);
    }

    /**
     * Récupère un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement recherché
     * @return une réponse HTTP contenant l'équipement si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun équipement ne correspond à cet identifiant
     */
    public ResponseEntity<Equipment> getById(int id) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);
        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalEquipment.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouvel équipement en base de données.
     *
     * @param newEquipment données de l'équipement à créer
     * @return une réponse HTTP contenant l'équipement créé (201 CREATED)
     */
    public ResponseEntity<Equipment> create(Equipment newEquipment) {
        newEquipment.setId(null);
        equipmentDao.save(newEquipment);
        return new ResponseEntity<>(newEquipment, HttpStatus.CREATED);
    }

    /**
     * Supprime un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun équipement ne correspond à cet identifiant
     */
    public ResponseEntity<Void> deleteById(int id) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipmentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un équipement existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'équipement à mettre à jour
     * @param equipmentToUpdate nouvelles données de l'équipement
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun équipement ne correspond à cet identifiant
     */
    public ResponseEntity<Void> updateById(int id, Equipment equipmentToUpdate) {
        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        equipmentToUpdate.setId(id);
        equipmentDao.save(equipmentToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
