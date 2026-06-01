package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.model.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {

    // récupère automatiquement le PasswordEncoder stocké dans le @Bean dans PasswordCOnfig
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
     * @return une réponse HTTP contenant l'utilisateur si il existe (200 OK),
     *         ou un statut 404 (NOT_FOUND) si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public ResponseEntity<AppUser> getAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);
        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalAppUser.get(), HttpStatus.OK);
    }

    /**
     * Crée un nouvel utilisateur en base de données.
     *
     * @param newAppUser données de l'utilisateur à créer
     * @return une réponse HTTP contenant l'utilisateur créé (201 CREATED)
     */
    @Override
    public ResponseEntity<AppUser> createAppUser(AppUser newAppUser) {
        newAppUser.setId(null);
        // on remplace le MDP en clair par la version hashée
        newAppUser.setPassword(encoder.encode(newAppUser.getPassword()));
        appUserDao.save(newAppUser);
        return new ResponseEntity<>(newAppUser, HttpStatus.CREATED);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur à supprimer
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la suppression est effectuée,
     *         ou 404 (NOT_FOUND) si aucun utilisateur ne correspond à cet identifiant
     */

    @Override
    public ResponseEntity<Void> deleteAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modificationDao.deleteAllByUserId(id); // A GERER COTE METIER
        requestDao.deleteAllByUserId(id);
        loanDao.deleteAllByUserId(id); // A GERER COTE METIER
        appUserDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Met à jour un utilisateur existant en remplaçant ses données.
     *
     * @param id identifiant unique de l'utilisateur à mettre à jour
     * @param appUserToUpdate nouvelles données de l'utilisateur
     * @return une réponse HTTP avec le statut 204 (NO_CONTENT) si la mise à jour est effectuée,
     *         ou 404 (NOT_FOUND) si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public ResponseEntity<Void> updateAppUser(int id, AppUser appUserToUpdate) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        appUserToUpdate.setId(id);
        appUserDao.save(appUserToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
