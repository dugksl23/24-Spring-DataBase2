package hello.itemservice.service;


import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemJpaRepository;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.query.ItemJdbcDto;
import hello.itemservice.repository.query.ItemJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Loader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemJdbcService {

    private final ItemJdbcRepository itemRepository;
    private final ItemJpaRepository itemJpaRepository;

    @Transactional
    public Integer saveAll(List<ItemJdbcDto> items) {
        return itemRepository.saveAll(items);
    }

    public Integer findAll() {
        List<Item> all = itemJpaRepository.findAll();
        log.info("size : {}", all.size());
        all.forEach(System.out::println);
        return all.size();
    }

}
