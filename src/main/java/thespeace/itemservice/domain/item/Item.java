package thespeace.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

//@Data //핵심 도메인에서 사용하기에는 위험, 필요한 것만 분리해서 사용하는 편이 안전.
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //nullable
    private Integer quantity;

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
