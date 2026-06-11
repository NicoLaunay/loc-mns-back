package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.EquipmentDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    @Autowired
    protected EquipmentDao equipmentDao;

    /**
     * Récupère l'ensemble des équipements enregistrés en base de données.
     *
     * @return une liste non nulle d'équipements, éventuellement vide si aucune donnée n'est présente
     */
    public List<Equipment> getAll() {
        return equipmentDao.findAll();
    }

    /**
     * Récupère l'ensemble des équipements d'un modèle disponibles sur la période indiquée.
     *
     * @param modelId   identifiant du modèle
     * @param startDate date de début de la période
     * @param endDate   date de fin de la période
     * @return une liste non nulle d'équipements disponibles, éventuellement vide
     */
    public List<Equipment> getAllOfModelAvailableOnPeriod(int modelId, LocalDate startDate, LocalDate endDate) {
        return equipmentDao.findAllOfModelAvailableOnPeriod(modelId, startDate, endDate);
    }

    /**
     * Récupère un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement recherché
     * @return l'équipement correspondant
     * @throws IdNotFoundException si aucun équipement ne correspond à cet identifiant
     */
    public Equipment get(int id) throws IdNotFoundException{
        return equipmentDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun équipement ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouvel équipement en base de données.
     *
     * @param newEquipment données de l'équipement à créer
     * @return l'équipement créé
     */
    public Equipment create(Equipment newEquipment) {
        newEquipment.setId(null);
        return equipmentDao.save(newEquipment);
    }

    /**
     * Supprime un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement à supprimer
     * @throws IdNotFoundException si aucun équipement ne correspond à cet identifiant
     */
    public void delete(int id) throws IdNotFoundException {
        equipmentDao.findById(id)
            .orElseThrow(() -> new IdNotFoundException("Aucun équipement ne correspond à cet identifiant"));
        equipmentDao.deleteById(id);
    }

    /**
     * Met à jour un équipement existant en remplaçant ses données.
     *
     * @param id              identifiant unique de l'équipement à mettre à jour
     * @param equipmentToUpdate nouvelles données de l'équipement
     * @throws IdNotFoundException si aucun équipement ne correspond à cet identifiant
     */
    public void update(int id, Equipment equipmentToUpdate) throws IdNotFoundException{
        equipmentDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun équipement ne correspond à cet identifiant"));
        equipmentToUpdate.setId(id);
        equipmentDao.save(equipmentToUpdate);
    }
}
