package com.ferdev.shoppingcart.repositories;

import com.ferdev.shoppingcart.entities.CartLine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartLineRepositoryImpl implements CartLineRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartLine> findAll() {
        TypedQuery<CartLine> query = this.entityManager.createQuery("SELECT c FROM CartLine c", CartLine.class);

        return query.getResultList();
    }

    @Override
    public Optional<CartLine> findById(int id) {
        CartLine cartLine = this.entityManager.find(CartLine.class, id);

        if (cartLine != null) {
            return Optional.of(cartLine);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CartLine> findByProductId(int productId) {
        TypedQuery<CartLine> query = this.entityManager.createQuery("SELECT c FROM CartLine c WHERE c.product.id = :id", CartLine.class);

        query.setParameter("id", productId);

        return query.getResultList();
    }


    @Override
    @Transactional
    public void save(CartLine cartLine) {
        this.entityManager.merge(cartLine);
    }

    @Override
    @Transactional
    public void delete(CartLine cartLine) {
        this.entityManager.remove(cartLine);
    }

    @Override
    @Transactional
    public void deleteAll() {
        Query query = this.entityManager.createQuery("DELETE FROM CartLine c");

        query.executeUpdate();
    }


}
