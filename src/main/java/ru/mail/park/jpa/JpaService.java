package ru.mail.park.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.exception.ServiceException;
import ru.mail.park.exception.UserExistsException;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.IAccountService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andry on 23.10.16.
 */

@Service
@Transactional
public class JpaService  implements IAccountService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserProfile addUser(String username, String password) throws UserExistsException, ServiceException {
        try {
            UserEntity entity = new UserEntity(username, password);

            em.persist(entity);
            return entity.toDto();
        } catch (javax.persistence.PersistenceException e)
        {
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new UserExistsException(e);
            }else {
                throw new ServiceException(e);
            }
        }
    }


    @Override
    public UserProfile getUser(String username){
        try {
            return ((UserEntity) em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :custUsername")
                    .setParameter("custUsername", username)
                    .setMaxResults(1).getSingleResult()).toDto();
        }catch(NoResultException e) {
            return null;
        }

    }

    @Override
    public List<UserProfile> getAllUsers(){
        return em.createQuery("select u from UserEntity u", UserEntity.class)
                .getResultList()
                .stream()
                .map(UserEntity::toDto)
                .collect(Collectors.toList());
    }
}
