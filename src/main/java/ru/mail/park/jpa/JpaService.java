package ru.mail.park.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.IAccountService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by Andry on 23.10.16.
 */

@Service
@Transactional
public class JpaService  implements IAccountService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserProfile addUser(String email, String password, String name){
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        entity.setName(name);
        entity.setPassword(password);

        em.persist(entity);
        return entity.toDto();
    }


    @Override
    public UserProfile getUser(String email){
        try {
            return ((UserEntity) em.createQuery("SELECT c FROM UserEntity c WHERE c.email = :custEmail")
                    .setParameter("custEmail", email)
                    .setMaxResults(1).getSingleResult()).toDto();
        }catch(NoResultException e){
            return null;
        }

    }




}
