package me.chonchol.androposweb.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseController {

    @PersistenceContext
    EntityManager entityManager;
}
