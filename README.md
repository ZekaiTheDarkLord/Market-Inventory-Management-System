# Market-Inventory-Management-System
README: 
Installation: 
Download the file attached on the canvas and click the jar to run the program. 
Click the CS3200.jar, login into your own SQL account. 
Open MySQL, create the database using the dump provided. 
Then login from CS3200.jar, creating a manager in the application. Then enter into the 
application. 
Technical specification
Environment: Java 11+
Software Needed: MySQL Workbench
Library: Java Swing, Java connector
Manipulations: 
At first, the market will ask to connect the MySQL with the username and password. 
After successfully login to the MySQL, it will ask for the manager information: 
Then the user has to enter the manager’s name and gender. The manager’s name will be 
remembered by the Market OS. For the next time login to the platform, the user has to 
provide the manager’s name in order to login into the platform. 
Then there is the page that shows all the operations that the Market OS can done. 
The user can interact with the Market OS by clicking the button. 
Buy: (if the storage is empty, the user cannot buy any item. So add item before buy item)
For “Buy” button, if the user clicks it, it will first let the user choose whether buy the item 
online or offline. 
For online operation, the user have to enter the item ID(which can be get through show 
storage) and the quantity they want. 
Then after the user confirm the operation(by clicking “确定” here, which means confirm), 
then the application will ask the user whether they want to create another transaction. 
By clicking the end button, the user chooses to stop this buy process. By clicking the 
continue button, the user can add another item into the transaction. 
After the edge has been added, the application will show the total cost for this transaction. 
Then the application will ask for whether the coupon has been added. 
If the user doesn’t have a coupon, they can click the confirm button(“确定” in here. These 
buttons are all depend on the language of operating system. So if the operating system is 
English, the buttons will show in English.)
Then the application will ask the user for payment information. For online transaction, the 
user can online pay through card. So they have to enter the card id and bank name. 
Then the user will ask for the card payment. The user has to enter the appropriate number of 
dollars. If the user pays the amount less than required, the transaction will fail. If the user 
enters the amount that greater than required, the money will not give back to the user’s 
account. 
After the payment has been entered, the application will ask for the address information and 
receiver information. 
Then, the user have to enter the shipping company. 
After the user click the confirm button, they can successfully finish this transaction and all 
the information will be showed in the GUI of the application. 
For offline transaction, they have all the process similar to online. However, they have two 
ways make the payment. 
The payment through card will similar to that of online payment. For cash, the user have to 
enter the cash amount they want to give. 
They after the payment, the user doesn’t have to enter the address and receiver information 
and the transaction is successfully created. 
The user can query the information they want by clicking different buttons. There are:
1) show online transactions: all the online transaction records can be found here
2) show online transaction items: all the corresponding online transaction items can be 
found here
3) show offline transactions: all the offline transaction records can be found here
4)
5) show offline transaction items: all the corresponding offline transaction items can be 
found here
6)
7) show card record: all the payment paid by card will be recorded and can be found here 
8) show cash record: all the payment paid by cash will be recorded and can be found here
9) show delivery record: all the delivery information can be found here 
10)show all coupon: all coupons information can be found here
11)show current manage coupon: show the coupon created by current manger
12)show storage: show all the items with item ID, name, quantity and price 
13)show employee: show all the employee information including employee ID, employee 
name, employee gender, job description and employee data 
Add Coupon: 
When add coupon button is clicked, the application will ask the user to enter the coupon 
password and the discount. And the information entered will be recorded in the database. 
Delete Coupon: 
When delete coupon button is clicked, the application will ask the user to enter the coupon 
password to delete the coupon given. And the information entered will be recorded in the 
database. 
Update Item: 
When the update item button is clicked the application will ask the user to enter the item id 
and the amount that should be decreased. And the information entered will be recorded in the 
database. And the information entered will be recorded in the database. 
Add Item: 
When the add item is clicked, the application will ask the user to enter the item name, item 
quantity and item price. And the information entered will be recorded in the database. 
Add employee: 
When the add employee button is clicked, the application will ask user to enter the 
employee’s name, job description and gender. And the information entered will be recorded 
in the database. 
Delete Employee: 
When the delete employee is clicked, the application will ask user to enter the employee’s 
name and employee ID. If invalid information is entered, the operation will fail. All the 
information entered will be recorded in the database. 
Change manager: 
When the change manager is clicked, the user has to enter the manager name and gender. 
When the manager is successfully changed, the information will change. 