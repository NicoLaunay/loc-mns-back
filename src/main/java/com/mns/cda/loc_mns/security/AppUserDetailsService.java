package com.mns.cda.loc_mns.security;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    protected final AppUserDao appUserDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<AppUser> optionalAppUser = appUserDao.findByEmail(email);

        // Si l'Email n'existe pas
        if (optionalAppUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        };

        return new AppUserDetails(optionalAppUser.get());

    }
}
