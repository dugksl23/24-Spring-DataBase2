package hello.itemservice.repository.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.domain.QItem.item;


@Repository
@Transactional(readOnly = true)
public class SpringQueryDslRepository implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public SpringQueryDslRepository(EntityManager entityManager) {
        this.em = entityManager;
        this.query = new JPAQueryFactory(em);
    }

    @Transactional
    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Transactional
    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        QItem item = new QItem(QItem.item); // 인자로 넣어준 String 이 alias 로 지정된다.

        List<Item> fetch = query
                .select(item)
                .from(item)
                .where(likeItemName(itemName), lessThanEqualPrice(maxPrice))
                .fetch();

        return fetch;
    }

    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.isEmpty(itemName)) {
            return null;
        }
        return item.itemName.like("%" + itemName + "%");
    }

    private BooleanExpression lessThanEqualPrice(Integer price) {
        if (price == null) {
            return null;
        }
        return item.price.loe(price);
    }
}
