package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    @Autowired
    protected RequestDao requestDao;

    public List<Request> getAllRequests() {
        return requestDao.findAll();
    }

    public ResponseEntity<Request> getRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);
        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalRequest.get(), HttpStatus.OK);
    }

    public ResponseEntity<Request> createRequest(Request newRequest) {
        newRequest.setId(null);
        requestDao.save(newRequest);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteRequest(int id) {
        Optional<Request> optionalRequest = requestDao.findById(id);

        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        requestDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateRequest(int id, Request requestToUpdate) {
        Optional<Request> optionalRequest = requestDao.findById(id);

        if (optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        requestToUpdate.setId(id);
        requestDao.save(requestToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
