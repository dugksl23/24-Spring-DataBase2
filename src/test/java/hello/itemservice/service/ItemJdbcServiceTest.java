package hello.itemservice.service;

import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.jdbcTemplate.ItemJdbcDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Commit
@SpringBootTest
class ItemJdbcServiceTest {

    @Autowired
    private ItemJdbcService itemJdbcService;

    @Value("${batchSize}")
    int batchSize;

    @Test
    @DisplayName("JDBC Bulk Insert Test")
    @Rollback(value = false)
    @Commit
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
        // when...
        Integer i = itemJdbcService.saveAll(items);

        ItemSearchCond cond = new ItemSearchCond();
        Integer all = itemJdbcService.findAll();
        // then...
        Assertions.assertThat(items.size()).isEqualTo(all);

    }


}