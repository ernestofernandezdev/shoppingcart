package com.ferdev.shoppingcart.repositories;

import com.ferdev.shoppingcart.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = this.entityManager.createQuery("SELECT p FROM Product p", Product.class);

        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(int id) {
        Product product = this.entityManager.find(Product.class, id);

        if (product != null) {
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

}
