package org.greengin.senseitweb.logic.persistence;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;

public class CustomEntityManagerFactory extends LocalContainerEntityManagerFactoryBean {

    private EntityManager entityManager;

    public CustomEntityManagerFactory() {
        entityManager = null;
    }

    public EntityManager createEntityManager() {
        if (entityManager == null) {
            entityManager = this.getNativeEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }
}