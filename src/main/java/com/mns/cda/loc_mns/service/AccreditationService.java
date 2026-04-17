package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AccreditationDao;
import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Crée un constructeur avec tous les champs requis (final)
public class AccreditationService {

    protected final AccreditationDao accreditationDao;
    private final TypeDao typeDao;

    public List<Accreditation> getAllAccreditations() {
        return accreditationDao.findAll();
    }

    public ResponseEntity<Accreditation> getAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);
        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalAccreditation.get(), HttpStatus.OK);
    }

    public ResponseEntity<Accreditation> createAccreditation(Accreditation newAccreditation) {
        newAccreditation.setId(null);

        updateBorrowedTypes(newAccreditation);

        accreditationDao.save(newAccreditation);
        return new ResponseEntity<>(newAccreditation, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteAccreditation(int id) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accreditationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateAccreditation(int id, Accreditation accreditationToUpdate) {
        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if (optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        accreditationToUpdate.setId(id);

        updateBorrowedTypes(accreditationToUpdate);

        accreditationDao.save(accreditationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Accreditation updateBorrowedTypes(Accreditation accreditation) {
        List<Type> newBorrowedTypes = new ArrayList<>();

        for (Integer typeId:accreditation.getBorrowedTypesIds()) {
            Type type = this.typeDao.findById(typeId)
                    .orElseThrow(() -> new RuntimeException("Il n'existe pas de type avec cet id : " + typeId));;
            newBorrowedTypes.add(type);
        }
        accreditation.setBorrowedTypes(newBorrowedTypes);
        return accreditation;
    }
}
