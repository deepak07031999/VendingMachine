package org.deepak.dto.states;

import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.Coin;
import org.deepak.dto.item.Item;
import org.deepak.service.VendingMachine;

import java.util.List;

@Slf4j
public class InsertMoneyState implements State {

    public InsertMoneyState() {
        log.info("Currently Vending machine is in InsertMoneyState");
    }
    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) throws Exception {
        return;
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) throws Exception {
        log.info("Coin inserted: {} (value: {})", coin, coin.value);
        machine.getCoinList().add(coin);
        int totalAmount = machine.getCoinList().stream().mapToInt(c -> c.value).sum();
        log.info("Total amount inserted: {}", totalAmount);
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine machine) throws Exception {
        log.info("Transitioning from InsertMoneyState to SelectionState");
        machine.setVendingMachineState(new SelectionState());
    }

    @Override
    public void chooseProduct(VendingMachine machine, int codeNumber) throws Exception {
        throw new Exception("you need to click on start product selection button first");
    }

    @Override
    public int returnChange(int returnChangeMoney) throws Exception {
        throw new Exception("you can not get change in InsertMoneyState state");
    }

    @Override
    public Item dispenseProduct(VendingMachine machine, int codeNumber) throws Exception {
        throw new Exception("product can not be dispensed in InsertMoneyState state");
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine machine) throws Exception {
        machine.setVendingMachineState(new IdleState());
        return machine.getCoinList();
    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        throw new Exception("you can not update inventory in insert money state");
    }
}
