package org.deepak.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.deepak.dto.Coin;
import org.deepak.dto.inventory.Inventory;
import org.deepak.dto.states.IdleState;
import org.deepak.dto.states.State;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Slf4j
public class VendingMachine {
    private State vendingMachineState;
    private Inventory inventory;
    private List<Coin> coinList;
    public VendingMachine(){
        log.info("Initializing vending machine");
        vendingMachineState = new IdleState();
        inventory = new Inventory(10);
        coinList = new ArrayList<>();
        log.info("Vending machine initialized successfully");
    }

}
