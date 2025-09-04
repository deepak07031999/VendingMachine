# VendingMachine

A Java implementation of a vending machine system using the State Design Pattern. This project demonstrates object-oriented programming principles, design patterns, and clean architecture.

## Features

- **State-based Operations**: Implements different states (Idle, Insert Money, Selection, Dispense)
- **Inventory Management**: Tracks items and quantities across multiple shelves
- **Coin Handling**: Supports multiple coin denominations (TWO, TEN)
- **Product Selection**: Interactive product selection with code-based identification
- **Change Calculation**: Automatic change return functionality

## Architecture

The system uses the **State Design Pattern** with the following states:
- `IdleState`: Initial state, waiting for user interaction
- `InsertMoneyState`: Accepting coins from user
- `SelectionState`: Product selection phase
- `DispenseState`: Product dispensing and change return

## Class Diagram

```mermaid
classDiagram
    class VendingMachine {
        -State vendingMachineState
        -Inventory inventory
        -List~Coin~ coinList
        +getVendingMachineState() State
        +getInventory() Inventory
    }
    
    class State {
        <<interface>>
        +clickOnInsertCoinButton(VendingMachine)
        +insertCoin(VendingMachine, Coin)
        +clickOnStartProductSelectionButton(VendingMachine)
        +chooseProduct(VendingMachine, int)
        +dispenseProduct(VendingMachine, int) Item
    }
    
    class IdleState {
        +clickOnInsertCoinButton(VendingMachine)
    }
    
    class InsertMoneyState {
        +insertCoin(VendingMachine, Coin)
        +clickOnStartProductSelectionButton(VendingMachine)
    }
    
    class SelectionState {
        +chooseProduct(VendingMachine, int)
    }
    
    class DispenseState {
        +dispenseProduct(VendingMachine, int) Item
    }
    
    class Inventory {
        -List~ItemShelf~ itemShelves
        +getItem(int) Item
        +dispenseItem(int) Item
        +addItem(Item, int)
    }
    
    class ItemShelf {
        -int itemShelfCode
        -List~Item~ items
        -ItemType itemType
        -int maxQuantity
        +getQuantity() int
    }
    
    class Item {
        -int price
        -ItemType itemType
    }
    
    class ItemType {
        <<enumeration>>
        COKE
        PEPSI
        JUICE
        SODA
    }
    
    class Coin {
        <<enumeration>>
        ONE(1)
        TWO(5)
        FIVE(5)
        TEN(10)
        +int value
    }
    
    VendingMachine --> State
    VendingMachine --> Inventory
    VendingMachine --> Coin
    State <|.. IdleState
    State <|.. InsertMoneyState
    State <|.. SelectionState
    State <|.. DispenseState
    Inventory --> ItemShelf
    ItemShelf --> Item
    Item --> ItemType
```

## Available Products

- **COKE**: ₹12 (Slots 0-2)
- **PEPSI**: ₹9 (Slots 3-4)
- **JUICE**: ₹13 (Slots 5-6)
- **SODA**: ₹7 (Slots 7-9)

## Requirements

- Java 21
- Maven 3.6+

## Dependencies

- Lombok 1.18.30
- SLF4J 2.0.9
- Logback 1.4.11

## Usage

```bash
mvn compile exec:java -Dexec.mainClass="org.deepak.Main"
```

The application will:
1. Initialize inventory with products
2. Accept coin insertion
3. Allow product selection via console input
4. Dispense product and return change