package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    /**
     * Récupère un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public AppUser get(int id) throws IdNotFoundException {
        return appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
    }

    /**
     * Récupère un utilisateur à partir de son adresse email.
     *
     * @param email adresse email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cette adresse email
     */
    @Override
    public AppUser getByEmail(String email) throws IdNotFoundException {
        return appUserDao.findByEmail(email)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cette adresse email"));
    }

    /**
     * Crée un nouvel utilisateur en base de données.
     *
     * @param newAppUser données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @Override
    public AppUser create(AppUser newAppUser) {
        newAppUser.setId(null);
        newAppUser.setPassword(encoder.encode(newAppUser.getPassword()));
        return appUserDao.save(newAppUser);
    }

    /**
     * Supprime un utilisateur à partir de son identifiant.
     *
     * @param id identifiant unique de l'utilisateur à supprimer
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void delete(int id) throws IdNotFoundException, AccessDeniedException {
        AppUser userToDelete = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));

        if (userToDelete.getRole().getName().equals("OWNER")) {
            throw new AccessDeniedException("Impossible de supprimer le propriétaire");
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
     * @throws IdNotFoundException si aucun utilisateur ne correspond à cet identifiant
     */
    @Override
    public void update(int id, AppUser appUserToUpdate) throws IdNotFoundException {
        AppUser oldAppUser = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        appUserToUpdate.setId(id);
        appUserToUpdate.setPassword(oldAppUser.getPassword());
        appUserDao.save(appUserToUpdate);
    }

    @Override
    public void changePassword(int id, String newPassword) throws IdNotFoundException {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        appUser.setPassword(encoder.encode(newPassword));
        appUserDao.save(appUser);
    }
}
