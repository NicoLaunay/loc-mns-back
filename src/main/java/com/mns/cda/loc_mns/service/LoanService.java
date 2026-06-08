package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
     * Récupère un emprunt à partir de son identifiant.
     *
     * @param id identifiant unique de l'emprunt recherché
     * @return l'emprunt s'il existe,
     *         ou une exception si aucun emprunt ne correspond à cet identifiant
     * @throws IllegalArgumentException exception signalant l'absence d'élément portant l'identifiant id
     */
    public Loan getById(int id) throws IllegalArgumentException {
        Optional<Loan> optionalLoan = loanDao.findById(id);
        if (optionalLoan.isEmpty()) {
            throw new IllegalArgumentException("Aucun prêt ne correspond à cet identifiant");
        } else {
            return optionalLoan.get();
        }
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
            throw new IllegalArgumentException("La date de fin doit êttre après la date de début");
        }

        return loanDao.save(newLoan);

    }

    /**
     * Supprime un emprunt à partir de son identifiant.
     *
     * @param id identifiant unique de l'emprunt à supprimer
     * @throws IllegalArgumentException exception signalant l'absence d'élément portant l'identifiant id
     */
    public void delete(int id) throws IllegalArgumentException {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            throw new IllegalArgumentException("Aucun prêt ne correspond à cet identifiant");
        } else {
            loanDao.deleteById(id);
        }
    }

    /**
     * Met à jour un emprunt existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'emprunt à mettre à jour
     * @param loanToUpdate nouvelles données de l'emprunt
     * @throws IllegalArgumentException exception signalant l'absence d'élément portant l'identifiant id
     */
    public void update(int id, Loan loanToUpdate) throws IllegalArgumentException {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            throw new IllegalArgumentException("Aucun prêt ne correspond à cet identifiant");
        } else {
            // On écrase l'id du JSON par celui en paramètre
            loanToUpdate.setId(id);
            loanDao.save(loanToUpdate);
        }
    }
}
