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
     * @return une réponse HTTP contenant l'emprunt si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun emprunt ne correspond à cet identifiant
     */
    public ResponseEntity<Loan> getById(int id) {
        Optional<Loan> optionalLoan = loanDao.findById(id);
        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLoan.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouvel emprunt en base de données.
     *
     * @param newLoan données de l'emprunt à créer
     * @return une réponse HTTP contenant l'emprunt créé (201 CREATED)
     */
    public ResponseEntity<Loan> create(Loan newLoan) {
        newLoan.setId(null);
        loanDao.save(newLoan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    /**
     * Supprime un emprunt à partir de son identifiant.
     *
     * @param id identifiant unique de l'emprunt à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun emprunt ne correspond à cet identifiant
     */
    public ResponseEntity<Void> delete(int id) {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un emprunt existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'emprunt à mettre à jour
     * @param loanToUpdate nouvelles données de l'emprunt
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun emprunt ne correspond à cet identifiant
     */
    public ResponseEntity<Void> update(int id, Loan loanToUpdate) {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        loanToUpdate.setId(id);
        loanDao.save(loanToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
