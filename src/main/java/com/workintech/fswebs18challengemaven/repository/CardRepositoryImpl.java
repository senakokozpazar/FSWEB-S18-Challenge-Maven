package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CardRepositoryImpl implements CardRepository {

    private final EntityManager entityManager;

    @Autowired
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Card save(Card card) {
        log.info("save started");
        entityManager.persist(card);
        log.info("save ended");
        return card;
    }


    @Override
    public List<Card> findByColor(String color) {
        // String parametresi ile color alanını LIKE operatörü ile kullanarak sorgu oluşturuyoruz
        TypedQuery<Card> query = entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.color LIKE CONCAT('%', :color, '%')",
                Card.class
        );
        query.setParameter("color", color); // color parametresini ayarlıyoruz
        List<Card> cards = query.getResultList();
        if (cards.isEmpty()) {
            throw new CardException("No cards found with the specified color.", HttpStatus.NOT_FOUND);
        }

        return cards;

    }



    @Override
    public List<Card> findAll() {
        TypedQuery<Card> foundAll =  entityManager.createQuery("SELECT c from Card c", Card.class
        );
        return foundAll.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.type = :type", Card.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.value = :value", Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }



    @Transactional
    @Override
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    public Card remove(long id) {
        Card card = entityManager.find(Card.class, id);
        if (card != null) {
            entityManager.remove(card);
        }
        return card;
    }
}
