package org.deepak.dto.states;

import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.Coin;
import org.deepak.dto.item.Item;
import org.deepak.service.VendingMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SelectionState implements State {

    public SelectionState(){
       log.info("Currently Vending machine is in SelectionState");
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) throws Exception {
        throw new Exception("you can not click on insert coin button in Selection state");
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) throws Exception {
        throw new Exception("you can not insert Coin in selection state");    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine machine) throws Exception {
        return;
    }

    @Override
    public void chooseProduct(VendingMachine machine, int codeNumber) throws Exception {
        // Sanitize input before logging to prevent log injection
        String sanitizedCode = String.valueOf(Math.max(0, codeNumber));
        log.info("Product selected: code {}", sanitizedCode);
        Item item = machine.getInventory().getItem(codeNumber);
        int paidByUser = 0;
        for(Coin coin : machine.getCoinList()){
            paidByUser = paidByUser + coin.value;
        }
        log.info("Product price: {}, Amount paid: {}", item.getPrice(), paidByUser);

        if(paidByUser < item.getPrice()) {
            log.error("Insufficient Amount, Product you selected is for price: " + item.getPrice() + " and you paid: " + paidByUser);
            refundFullMoney(machine);
            throw new Exception("insufficient amount");
        }
        else if(paidByUser >= item.getPrice()) {

            if(paidByUser > item.getPrice()) {
                log.info("Returning change: {}", paidByUser-item.getPrice());
                returnChange(paidByUser-item.getPrice());
            }
            log.info("Transitioning to DispenseState for product code {}", sanitizedCode);
            machine.setVendingMachineState(new DispenseState(machine, codeNumber));
        }
    }

    @Override
    public int returnChange(int returnChangeMoney) throws Exception {
        log.info("Calculating change: {}", returnChangeMoney);

        // Calculate coins to return (greedy algorithm)
        List<Coin> changeCoins = new ArrayList<>();
        int remaining = returnChangeMoney;

        // Use available coin denominations in descending order
        for (Coin coinType : Arrays.asList(Coin.TEN, Coin.FIVE, Coin.TWO, Coin.ONE)) {
            while (remaining >= coinType.value) {
                changeCoins.add(coinType);
                remaining -= coinType.value;
            }
        }

        if (remaining > 0) {
            throw new Exception("Cannot provide exact change");
        }

        log.info("Dispensing change coins: {}", changeCoins);
        // In real implementation, this would trigger physical coin dispensing
        return returnChangeMoney;

    }

    @Override
    public Item dispenseProduct(VendingMachine machine, int codeNumber) throws Exception {
        throw new Exception("product can not be dispensed Selection state");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine machine) throws Exception {
        log.info("Returned the full amount back in the Coin Dispense Tray");
        machine.setVendingMachineState(new IdleState(machine));
        return machine.getCoinList();

    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        throw new Exception("you can not update inventory in hasMoney  state");
    }
}
