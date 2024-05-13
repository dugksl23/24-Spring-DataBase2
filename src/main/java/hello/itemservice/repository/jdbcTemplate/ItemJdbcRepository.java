package hello.itemservice.repository.jdbcTemplate;


import hello.itemservice.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${batchSize}")
    private int batchSize;

    public Integer saveAll(List<ItemJdbcDto> items) {
        int batchCount = 0;
        List<Item> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            subItems.add(items.get(i).toEntity());

            //주어진 리스트(subItems)를 batchSize만큼씩 나누어서 일괄 삽입(batchInsert)을 수행
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsert(batchSize, batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchSize, batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
        return batchCount;
    }

    private int batchInsert(int batchSize, int batchCount, List<Item> subItems) {

        String sql ="insert into item (item_name, price, quantity) values (?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, subItems.get(i).getItemName());
                ps.setInt(2, subItems.get(i).getPrice());
                ps.setInt(3, subItems.get(i).getQuantity());
            }

            @Override
            public int getBatchSize() {
                return subItems.size();
            }
        });

        subItems.clear();
        batchCount++;
        return batchCount;
    }



}
