package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpringDataJpaRepository implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    @Override
    public Item save(Item item) {
        log.info("jpa repository");
        return itemJpaRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemJpaRepository.updateItem(itemId, updateParam.getItemName(), updateParam.getPrice(), updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemJpaRepository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        return itemJpaRepository.findAll();
    }

    public List<Item> findItemsWithCond(ItemSearchCond itemSearch) {
        return itemJpaRepository.findItems(itemSearch.getItemName(), itemSearch.getMaxPrice());
    }
}
