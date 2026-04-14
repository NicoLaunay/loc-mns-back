package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.dto.TypeDto;
import com.mns.cda.loc_mns.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {

    @Autowired
    protected TypeDao typeDao;

    public List<Type> getAllTypes() {
        return typeDao.findAll();
    }

    public ResponseEntity<Type> getType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);
        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalType.get(), HttpStatus.OK);
    }

    public ResponseEntity<Type> createType(Type newType) {
        newType.setId(null);
        typeDao.save(newType);
        return new ResponseEntity<>(newType, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteType(int id) {
        Optional<Type> optionalType = typeDao.findById(id);

        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateType(int id, Type typeToUpdate) {
        Optional<Type> optionalType = typeDao.findById(id);

        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        typeToUpdate.setId(id);
        typeDao.save(typeToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
