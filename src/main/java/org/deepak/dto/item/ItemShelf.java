package org.deepak.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ItemShelf {
    private int itemShelfCode;
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    @Builder.Default
    private ItemType itemType = null;
    @Builder.Default
    private int quantity = 0;
    @Builder.Default
    private int maxQuantity = 10;

    public int getQuantity(){
        return items.size();
    }
}
