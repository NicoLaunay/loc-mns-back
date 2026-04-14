package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.DocumentationDao;
import com.mns.cda.loc_mns.model.Documentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentationService {

    @Autowired
    protected DocumentationDao documentationDao;

    public List<Documentation> getAllDocumentations() {
        return documentationDao.findAll();
    }

    public ResponseEntity<Documentation> getDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);
        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalDocumentation.get(), HttpStatus.OK);
    }

    public ResponseEntity<Documentation> createDocumentation(Documentation newDocumentation) {
        newDocumentation.setId(null);
        documentationDao.save(newDocumentation);
        return new ResponseEntity<>(newDocumentation, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteDocumentation(int id) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        documentationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateDocumentation(int id, Documentation documentationToUpdate) {
        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if (optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        documentationToUpdate.setId(id);
        documentationDao.save(documentationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
