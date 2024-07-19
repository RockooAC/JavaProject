package org.gbbv.musikkspring.repository;


import org.gbbv.musikkspring.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserIdOrderByCreatedDateDesc(Integer userId);

    void deleteAllByUserId(Integer id);

    List<Cart> findAllByUserId(int userId);

}