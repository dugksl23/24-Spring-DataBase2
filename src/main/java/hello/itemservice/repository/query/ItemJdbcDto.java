package hello.itemservice.repository.query;

import hello.itemservice.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemJdbcDto {

    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item toEntity() {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        return new Item(itemName, price, quantity);
    }

}
