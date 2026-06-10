package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {

    private final PasswordEncoder encoder;

    @Autowired
    protected final AppUserDao appUserDao;
    @Autowired
    private LoanDao loanDao;
    @Autowired
    private RequestDao requestDao;
    @Autowired
    private ModificationDao modificationDao;

    /**
     * Récupère l'ensemble des utilisateurs enregistrés en base de données.
     *
     * @return une liste non nulle d'utilisateurs, éventuellement vide si aucune donnée n'est présente
     */
    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserDao.findAll();
    }

    /**
     * Récupère un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public AppUser getAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);
        if (optionalAppUser.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur ne correspond à cet identifiant");
        }
        return optionalAppUser.get();
    }

    /**
     * Récupère un utilisateur à partir de son identifiant.
     *
     * @param email email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public AppUser getAppUserByEmail(String email) {
        Optional<AppUser> optionalAppUser = appUserDao.findByEmail(email);
        if (optionalAppUser.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur ne correspond à cet identifiant");
        }
        return optionalAppUser.get();
    }

    /**
     * Crée un nouvel utilisateur en base de données.
     *
     * @param newAppUser données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @Override
    public AppUser createAppUser(AppUser newAppUser) {
        newAppUser.setId(null);
        newAppUser.setPassword(encoder.encode(newAppUser.getPassword()));
        return appUserDao.save(newAppUser);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur à supprimer
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void deleteAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);
        if (optionalAppUser.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur ne correspond à cet identifiant");
        }
        modificationDao.deleteAllByUserId(id);
        requestDao.deleteAllByUserId(id);
        loanDao.deleteAllByUserId(id);
        appUserDao.deleteById(id);
    }

    /**
     * Met à jour un utilisateur existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'utilisateur à mettre à jour
     * @param appUserToUpdate nouvelles données de l'utilisateur
     * @throws IllegalArgumentException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void updateAppUser(int id, AppUser appUserToUpdate) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);
        if (optionalAppUser.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur ne correspond à cet identifiant");
        }
        appUserToUpdate.setId(id);
        appUserDao.save(appUserToUpdate);
    }
}
