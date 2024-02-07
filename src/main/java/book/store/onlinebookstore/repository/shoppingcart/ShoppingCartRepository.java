package book.store.onlinebookstore.repository.shoppingcart;

import book.store.onlinebookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book b "
            + "WHERE sc.user.id = (:id)")
    ShoppingCart getShoppingCartByUserId(Long id);
}
