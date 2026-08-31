package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.EquipmentDao;
import com.mns.cda.loc_mns.dto.EquipmentDto;
import com.mns.cda.loc_mns.dto.EquipmentNoLoansDto;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.mapper.EquipmentMapper;
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

    private final EquipmentDao equipmentDao;

    private final EquipmentMapper mapper;

    /**
     * Récupère l'ensemble des équipements enregistrés en base de données.
     *
     * @return une liste non nulle d'équipements sans leurs prêts sous forme de DTO, éventuellement vide si aucune donnée n'est présente
     */
    public List<EquipmentNoLoansDto> getAll(){
        return equipmentDao.findAll()
                .stream()
                .map(mapper::toNoLoansDto)
                .toList();
    }

    /**
     * Récupère l'ensemble des équipements enregistrés en base de données.
     *
     * @return une liste non nulle d'équipements sous forme de DTO, éventuellement vide si aucune donnée n'est présente
     */
    public List<EquipmentDto> getAllWithLoans(){
        return equipmentDao.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Récupère l'ensemble des équipements d'un modèle disponibles sur la période indiquée.
     *
     * @param modelId   identifiant du modèle
     * @param startDate date de début de la période
     * @param endDate   date de fin de la période
     * @return une liste non nulle d'équipements disponibles, éventuellement vide
     */
    public List<EquipmentNoLoansDto> getAllOfModelAvailableOnPeriod(int modelId, LocalDate startDate, LocalDate endDate) {
        return equipmentDao.findAllOfModelAvailableOnPeriod(modelId, startDate, endDate)
                .stream()
                .map(mapper::toNoLoansDto)
                .toList();
    }

    /**
     * Récupère un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement recherché
     * @return l'équipement correspondant
     * @throws IdNotFoundException si aucun équipement ne correspond à cet identifiant
     */
    public EquipmentNoLoansDto get(int id) throws IdNotFoundException{
        return equipmentDao.findById(id)
                .map(mapper::toNoLoansDto)
                .orElseThrow(() -> new IdNotFoundException("Aucun équipement ne correspond à cet identifiant"));
    }

    /**
     * Récupère un équipement à partir de son identifiant.
     *
     * @param id identifiant unique de l'équipement recherché
     * @return l'équipement correspondant
     * @throws IdNotFoundException si aucun équipement ne correspond à cet identifiant
     */
    public EquipmentDto getWithLoans(int id) throws IdNotFoundException{
        return equipmentDao.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IdNotFoundException("Aucun équipement ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouvel équipement en base de données.
     *
     * @param newEquipment données de l'équipement à créer
     * @return l'équipement créé
     */
    public EquipmentDto create(Equipment newEquipment) {
        newEquipment.setId(null);
        equipmentDao.save(newEquipment);
        return mapper.toDto(newEquipment);
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
