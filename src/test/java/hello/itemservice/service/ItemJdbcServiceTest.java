package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemJpaRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.query.ItemJdbcDto;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ItemJdbcServiceTest {

    @Autowired
    private ItemJdbcService itemJdbcService;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Value("${batchSize}")
    int batchSize;

    @Test
    @DisplayName("JDBC Bulk Insert Test")
    @Rollback(false)
    void bulkInsert() {

        // given...
        List<ItemJdbcDto> items = new ArrayList<>();
        System.out.println("batchSize: " + batchSize);
        for (int i = 0; i < batchSize; i++) {
            ItemJdbcDto itemJdbcDto = new ItemJdbcDto();
            itemJdbcDto.setItemName("dto" + i);
            itemJdbcDto.setQuantity(i);
            itemJdbcDto.setPrice(i);
            items.add(itemJdbcDto);
        }
        System.out.println("Items Size: " + items.size());
        // when...
        Integer i = itemJdbcService.saveAll(items);

        ItemSearchCond cond = new ItemSearchCond();
        Integer all = itemJdbcService.findAll();
        System.out.println("select Size: " + all);
        // then...
        Assertions.assertThat(items.size()).isEqualTo(all);

    }


}