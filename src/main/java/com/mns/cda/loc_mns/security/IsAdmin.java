package com.mns.cda.loc_mns.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// indique les types d'objets sur lesquels on peut mettre l'annotation
@Target({ElementType.METHOD, ElementType.TYPE})
// indique quand l'annotation est effectuée
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public @interface IsAdmin {
}
