


#  Advanced Mobile Inventory System <img src="http://img.shields.io/badge/-Java-F89820?style=flat&logo=java&logoColor=white">

<img src = "/images/1.jpg" height="25%" width="25%"><img src = "/images/2.jpg" height="25%" width="25%"><img src = "/images/3.jpg" height="25%" width="25%"><img src = "/images/4.jpg" height="25%" width="25%">
<br/>

I developed a mobile app that takes bill of material and the gross requirements of the end item along with the stock, scheduled receipt, arrival on week, lead time and lot sizing rule information of the subcomponents to create MRP records for all parts and components.
  
This project has been developed using the **sqlite database**.
- For console version of the project [Link](https://github.com/yasintohan/MRP-Inventory-System)
- For the mobile version of the project with SharedPreferences [Link](https://github.com/yasintohan/MRP-Inventory-System-Mobile)


## System Operation

  
There are 4 buttons in the main activity of application. These are **"INVENTORY"**, **"ORDER ITEM"**, **"ADD ITEM"** and **"CLEAR DATABASE"** buttons.

### "INVENTORY" button
When the button is pressed, the inventory page opens and you can see the current stock status.
>The data are recorded in the system as tables with the **sqlite database**.

<br/><img src = "/images/2.jpg" height="30%" width="30%">

### "ORDER ITEM" button
When the button is pressed, the ordering page opens. Order is made by following the steps below.
1. User enters the id of the item he wants to order in the "Item id" section.
2. The user writes down how many pieces he wants for the weeks he wants to order.
3. The user presses the "order item" button.
4. If the selected item exists in the system, the item entered as input is assigned as the current item. If there are no items in the system, the 1st stage will be returned.
5. Calculations for the entered item are returned.
6. Calculations are printed on the screen in tabular form.
7. Sub-item test is made for the current item, if there are subitems, calculations are made for the subitems and printed on the screen.

<br/><img src = "/images/3.jpg" height="30%" width="30%">

### "ADD ITEM" button  
When the button is pressed, the product adding page opens and when you fill in the required fields, the product is added.
>The value in the **spinner** indicates which product is the main product of the product to be added

<br/><img src = "/images/4.jpg" height="30%" width="30%">

### "CLEAR DATABASE" button
When the button is pressed, it deletes all records in the system.

## SQL Tables

**ITEM TABLE**
| Field | Type | Key |
| ------ | ------ | ------ |
| KEY_ID | INTEGER | PK |
| KEY_ITEM_ID | TEXT|  |
| KEY_AON_HAND| INTEGER  |  |
| KEY_SCH_RCP | INTEGER  |  |
| KEY_ARON_WEEK | INTEGER  |  |
| KEY_LEAD_TIME | INTEGER  |  |
| KEY_LS_RULE | INTEGER  |  |
| KEY_REQUIRED | INTEGER  |  |
---
**CHILDS TABLE**
| Field | Type | Key |
| ------ | ------ | ------ |
| KEY_ID | INTEGER | PK |
| KEY_MAIN_ITEM_ID | INTEGER|  |
| KEY_CHILD_ID | INTEGER  |  |

---

by [Yasin Tohan](https://github.com/yasintohan)
