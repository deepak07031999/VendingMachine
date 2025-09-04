package org.deepak.dto.states;

import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.Coin;
import org.deepak.dto.item.Item;
import org.deepak.service.VendingMachine;

import java.util.List;

@Slf4j
public class DispenseState implements  State {

    public DispenseState(VendingMachine machine, int codeNumber) throws Exception{
        log.info("Currently Vending machine is in DispenseState");
        dispenseProduct(machine, codeNumber);
    }


    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) throws Exception {
        throw new Exception("insert coin button can not clicked on Dispense state");
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) throws Exception {
        throw new Exception("coin can not be inserted in Dispense state");
    }

    @Override
    public void clickOnStartProductSelectionButton(VendingMachine machine) throws Exception {
        throw new Exception("product selection buttion can not be clicked in Dispense state");
    }

    @Override
    public void chooseProduct(VendingMachine machine, int codeNumber) throws Exception {
        throw new Exception("product can not be choosen in Dispense state");
    }

    @Override
    public int returnChange(int returnChangeMoney) throws Exception {
        throw new Exception("change can not returned in Dispense state");
    }

    @Override
    public Item dispenseProduct(VendingMachine machine, int codeNumber) throws Exception {
        log.info("Dispensing product with code: {}", codeNumber);
        Item item = machine.getInventory().dispenseItem(codeNumber);
        log.info("Product dispensed successfully: {} - Price: {}", item.getItemType(), item.getPrice());
        log.info("Transitioning back to IdleState");
        machine.setVendingMachineState(new IdleState(machine));
        return item;

    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine machine) throws Exception {
        throw new Exception("refund can not be happen in Dispense state");
    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        throw new Exception("inventory can not be updated in Dispense state");
    }
}
