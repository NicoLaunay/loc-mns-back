package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.CompositionDao;
import com.mns.cda.loc_mns.model.Composition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompositionService {

    @Autowired
    protected CompositionDao compositionDao;

    public List<Composition> getAllCompositions() {
        return compositionDao.findAll();
    }

    public ResponseEntity<Composition> getComposition(Composition.Key key) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);
        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalComposition.get(), HttpStatus.OK);
    }

    public ResponseEntity<Composition> createComposition(Composition newComposition) {
        newComposition.setId(null);
        compositionDao.save(newComposition);
        return new ResponseEntity<>(newComposition, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteComposition(Composition.Key key) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);

        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        compositionDao.deleteById(key);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateComposition(Composition.Key key, Composition compositionToUpdate) {
        Optional<Composition> optionalComposition = compositionDao.findById(key);

        if (optionalComposition.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        compositionToUpdate.setId(key);
        compositionDao.save(compositionToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
