package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNameLike(String itemName);

    List<Item> findByPriceLessThanEqual(Integer price);

    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);

    @Query("select i from Item i where i.itemName like :itemName or i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);

//
//    @Modifying
//    @Query("update Item i set i.itemName = :itemName, i.price = :price, i.quantity = :quantity where i.id= :id")
//    List<Item> updateItem(@Param("id") Long id, @Param("itemName") String itemName, @Param("price") Integer price, @Param("quantity") Integer quantity);

    @Modifying
    @Query("update Item i set i.itemName = :itemName, i.price = :price, i.quantity = :quantity where i.id= :id")
    void updateItem(@Param("id") Long id, @Param("itemName") String itemName, @Param("price") Integer price, @Param("quantity") int quantity);

}
