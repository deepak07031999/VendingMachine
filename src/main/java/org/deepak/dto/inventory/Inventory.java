package org.deepak.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.item.Item;
import org.deepak.dto.item.ItemShelf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class Inventory {
    private List<ItemShelf> itemShelves;

    public Inventory(int itemCount) {
        this.itemShelves = new ArrayList<>(Collections.nCopies(itemCount, null));
        initialEmptyInventory();
    }
    private void initialEmptyInventory(){
        int startCode = 101;
        for (int i = 0; i < this.itemShelves.size(); i++) {
            ItemShelf space =  ItemShelf.builder()
                    .itemShelfCode(startCode)
                    .build();
            itemShelves.set(i, space);
            log.info("ItemShelf code {} has been initialized.", startCode);
            startCode++;
        }
    }

    public Item getItem(int codeNumber) throws Exception{
        for(ItemShelf itemShelf : itemShelves){
            if(itemShelf.getItemShelfCode() == codeNumber){
                if(itemShelf.getQuantity()>0){
                    Item Item = itemShelf.getItems().stream().findFirst().orElse(null);
                    log.info("Item found: {} from shelf {}, quantity available: {}", Item.getItemType(), codeNumber, itemShelf.getQuantity());
                    return Item;
                }
                else {
                    log.error("ItemShelf {} is empty", codeNumber);
                    throw new RuntimeException("ItemShelf is empty.");
                }
            }
        }
        log.error("Item with code {} not found in inventory", codeNumber);
        throw new RuntimeException("Item not found in inventory.");
    }

    public void addItem(Item item, int codeNumber){
        for(ItemShelf itemShelf : itemShelves){
            if(itemShelf.getItemShelfCode() == codeNumber){
                if(itemShelf.getMaxQuantity()==itemShelf.getQuantity()) {
                    log.error("ItemShelf is full.");
                    throw new RuntimeException("ItemShelf is full.");
                }
                else itemShelf.getItems().add(item);
            }
        }
    }

    public Item dispenseItem(int codeNumber) throws Exception{
        for(ItemShelf itemShelf : itemShelves){
            if(itemShelf.getItemShelfCode() == codeNumber){
                if(itemShelf.getQuantity()>0){
                    Item Item = itemShelf.getItems().stream().findFirst().orElse(null);
                    itemShelf.getItems().removeFirst();
                    log.info("Item {} dispensed from shelf {}, remaining quantity: {}", Item.getItemType(), codeNumber, itemShelf.getQuantity());
                    return Item;
                }
                else {
                    log.error("Cannot dispense - ItemShelf {} is empty", codeNumber);
                    throw new RuntimeException("ItemShelf is empty.");
                }
            }
        }
        log.error("Cannot dispense - Item with code {} not found", codeNumber);
        throw new RuntimeException("Item not found in inventory.");
    }
}

