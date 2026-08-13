package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.exception.IncoherentDateException;
import com.mns.cda.loc_mns.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    @Autowired
    protected LoanDao loanDao;

    @Autowired
    protected AppUserDao userDao;

    /**
     * Récupère l'ensemble des emprunts enregistrés en base de données.
     *
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    /**
     * Récupère l'ensemble des emprunts en retard enregistrés en base de données.
     *
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getAllLate() {
        return loanDao.findAllLate();
    }

    /**
     * Récupère l'ensemble des emprunts, effectués par un utilisateur donné, enregistrés en base de données.
     *
     * @param userId identifiant unique de l'utilisateur concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getAllByUserId(Integer userId) {
        return loanDao.findAllByUserId(userId);
    }

    /**
     * Récupère l'ensemble des emprunts cloturés effectués par un utilisateur donné, enregistrés en base de données.
     *
     * @param userId identifiant unique de l'utilisateur concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getPastByUserId(Integer userId) {
        return loanDao.findPastByUserId(userId);
    }

    /**
     * Récupère l'ensemble des emprunts en cours effectués par un utilisateur donné, enregistrés en base de données.
     *
     * @param userId identifiant unique de l'utilisateur concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getOngoingByUserId(Integer userId) {
        return loanDao.findOngoingByUserId(userId);
    }

    /**
     * Récupère l'ensemble des emprunts planifiés par un utilisateur donné, enregistrés en base de données.
     *
     * @param userId identifiant unique de l'utilisateur concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getPlannedByUserId(Integer userId) {
        return loanDao.findPlannedByUserId(userId);
    }

    /**
     * Récupère l'ensemble des emprunts en retard effectués par un utilisateur donné, enregistrés en base de données.
     *
     * @param userId identifiant unique de l'utilisateur concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getLateByUserId(Integer userId) {
        return loanDao.findLateByUserId(userId);
    }

    /**
     * Récupère l'ensemble des emprunts, concernant un équipement donné, enregistrés en base de données.
     *
     * @param equipmentId identifiant unique de l'équipement concerné
     * @return une liste non nulle d'emprunts, éventuellement vide si aucune donnée n'est présente
     */
    public List<Loan> getAllByEquipmentId(Integer equipmentId) {
        return loanDao.findAllByEquipmentId(equipmentId);
    }

    /**
     * Récupère un emprunt à partir de son identifiant.
     *
     * @param id identifiant unique de l'emprunt recherché
     * @return l'emprunt s'il existe,
     *         ou une exception si aucun emprunt ne correspond à cet identifiant
     * @throws IdNotFoundException exception signalant l'absence d'élément portant l'identifiant id
     */
    public Loan get(int id) throws IdNotFoundException {
        return loanDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun prêt ne correspond à cet identifiant"));
    }

    /**
     * Crée un nouvel emprunt en base de données.
     *
     * @param newLoan données de l'emprunt à créer
     * @return l'emprunt créé
     * @throws IllegalArgumentException exception signalant l'absence d'élément portant l'identifiant id
     */
    public Loan create(Loan newLoan) throws IllegalArgumentException {
        LocalDate startDate = newLoan.getStartDate();
        LocalDate endDate = newLoan.getEndDate();

        if (!startDate.isBefore(endDate)) {
            throw new IncoherentDateException("La date de fin doit être après la date de début");
        }

        // Recontrôle serveur de la disponibilité
        if (loanDao.existsOverlappingByEquipmentId(newLoan.getEquipment().getId(), startDate, endDate)) {
            throw new IllegalArgumentException("Cet équipement est déjà emprunté sur la période demandée");
        }

        return loanDao.save(newLoan);

    }

    /**
     * Supprime un emprunt à partir de son identifiant.
     *
     * @param id identifiant unique de l'emprunt à supprimer
     * @throws IdNotFoundException exception signalant l'absence d'élément portant l'identifiant id
     */
    public void delete(int id) throws IdNotFoundException {
        loanDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun prêt ne correspond à cet identifiant"));
        loanDao.deleteById(id);
    }

    /**
     * Met à jour un emprunt existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'emprunt à mettre à jour
     * @param loanToUpdate nouvelles données de l'emprunt
     * @throws IdNotFoundException exception signalant l'absence d'élément portant l'identifiant id
     */
    public void update(int id, Loan loanToUpdate) throws IdNotFoundException {
        loanDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun prêt ne correspond à cet identifiant"));
        loanToUpdate.setId(id);
        loanDao.save(loanToUpdate);
    }
}
