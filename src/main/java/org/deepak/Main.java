package org.deepak;

import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.Coin;
import org.deepak.dto.item.Item;
import org.deepak.dto.item.ItemShelf;
import org.deepak.dto.item.ItemType;
import org.deepak.dto.states.State;
import org.deepak.service.VendingMachine;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {

        try{
            VendingMachine vendingMachine = new VendingMachine();
            log.info("filling up the inventory");
            fillUpInventory(vendingMachine);
            displayInventory(vendingMachine);

            log.info("clicking on InsertCoinButton");
            State vendingState = vendingMachine.getVendingMachineState();
            vendingState.clickOnInsertCoinButton(vendingMachine);
            vendingState = vendingMachine.getVendingMachineState();
            log.info("vendingState: {}", vendingMachine.getVendingMachineState());
            vendingState.insertCoin(vendingMachine, Coin.TEN);
            vendingState.insertCoin(vendingMachine, Coin.TWO);

            log.info("Enter on ProductSelectionButton");
            vendingState.clickOnStartProductSelectionButton(vendingMachine);
            vendingState = vendingMachine.getVendingMachineState();
            log.info("vendingState: {}", vendingState);

            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Enter product code: ");
                int choice = scanner.nextInt();
                // Validate input range before processing
                if (choice <100) {
                    log.warn("Invalid product code entered: {}",  choice);
                    throw new IllegalArgumentException("Product code must be between 101 and 110");
                }
                vendingState.chooseProduct(vendingMachine, choice);
            } catch (InputMismatchException e) {
                log.error("Please enter a valid number", e);
                scanner.nextLine(); // Clear invalid input
            } finally {
                scanner.close();
            }

            displayInventory(vendingMachine);

        } catch (Exception e) {
            log.error("Vending machine operation failed", e);
            throw new RuntimeException("Vending machine operation failed", e);
        }
    }

    private static void fillUpInventory(VendingMachine vendingMachine){
        List<ItemShelf> slots = vendingMachine.getInventory().getItemShelves();
        for (int i = 0; i < slots.size(); i++) {
            Item newItem = new Item();
            if(i >=0 && i<3) {
                newItem.setItemType(ItemType.COKE);
                newItem.setPrice(12);
            }else if(i >=3 && i<5){
                newItem.setItemType(ItemType.PEPSI);
                newItem.setPrice(9);
            }else if(i >=5 && i<7){
                newItem.setItemType(ItemType.JUICE);
                newItem.setPrice(13);
            }else if(i >=7 && i<10){
                newItem.setItemType(ItemType.SODA);
                newItem.setPrice(7);
            }
            slots.get(i).getItems().add(newItem);
        }
    }
    private static void displayInventory(VendingMachine vendingMachine){

        List<ItemShelf> slots = vendingMachine.getInventory().getItemShelves();
        for (ItemShelf shelf : slots) {
            if (!shelf.getItems().isEmpty()) {
                Item item = shelf.getItems().getFirst();
                log.info("CodeNumber: {} Item: {} Price: {} Quantity: {}",
                        shelf.getItemShelfCode(), item.getItemType().name(),
                        item.getPrice(), shelf.getQuantity());
            } else {
                log.info("CodeNumber: {} Item: {} Price: {} Quantity: {}",
                        shelf.getItemShelfCode(), "N/A",
                        "N/A", shelf.getQuantity());
            }
        }
    }


}