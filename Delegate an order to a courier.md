## Delegate an order to a courier

1. ~~Create an order when a customer enter a restaurant~~
2. Send this order as a <u>waiting order</u> to the order list in the system after customer pays the order 
3. system tries to find a courier according to different policies
   - ~~Find the available couriers~~
     - ~~on duty~~
     - ~~doesn't have another order to do~~
     - ~~has not been notified for this order before~~  
   - ~~Verify the policy settled by the manager~~
   - ~~The least distance policy~~  
     - ~~Calculate the distance between the courier and the restaurant~~  
     - ~~Give a list which contains all the couriers with the least distance on the top~~ 
   - ~~The fair delegating policy~~  
     - ~~get the number of order which has been finished by every courier~~ 
     - ~~give a list with the least number of finished order on the top~~


4. Delegation

   1. If we find a courier
      1. ~~Set this courier has been notified by this order~~
         - It means the system will not notify this courier with this order again
      2. ~~Notify this courier that he has new order to work~~
   2. If we do not find a courier
      1. ~~Send a message to manager that we don't have an appropriate courier for this order~~
         - leave this order's state as <u>waiting</u>

5. Decision made by the courier

   1. ~~Courier login and find a new order to do~~
      - ~~restriction: a courier can only choose an order to deliver at one time~~
   2. If this courier wants to accept this order
      1. Connect the courier to the order
         1. ~~find the order saved in the system and update the state~~
         2. ~~Add this order to the history of order of courier~~
      2. ~~Update the counter of order finished~~ 
      3. ~~Change the position~~
   3. If the courier wants to refuse this order
      1. Add this order to the refusing list
      2. Call the systeme to delegate a new courier for this order

















