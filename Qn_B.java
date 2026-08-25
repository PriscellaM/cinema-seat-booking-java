//Priscella Maenar 104511548 - COS10033 Assignment 1 Qn B

/*	The program is a management system for an insurance company. It collects data from the user input
to to create lists of customers and their insurance policies. Two classes are used in the program, Customer
class and Policy class. Inside the customer class, policy class is used to add policies to the customers.
	Upon running the program, there is a main menu prompt. There are several options, in the menu, for
customers to choose from. The choice is then used in a switch case for the menu. In addition to that, a do
while loop is also used to keep the menu looping until the exit option is chosen. 
	The program will ask for customer details and user will input name and address. The name and address
will then be in creating the customer instance, cust[0] = new Customer(name,address). The cust ID will
be created automatically, when customer instance is created, starting from C1 and it will increment by 1(
i.e C2, C3 and so on) when new customer is added.  Each customer can have maximum 5 policies, for this, an
instance variable Policy[] policy is added in the Customer class. This "Policy" is from Policy class which
is used for the insurance policy details. Policy details will be obtained by collecting data from user input
and then policy instance is created, Policy pol = new Policy(type, coverValue, duration, installmentAmt, plan, date).
That policy will then be added to that customer by using cust[0].addPol(pol) method (method in Customer class). 
	The program also enable users to change their policy details. The program will ask user to enter customer
ID to make changes. The user input ID will then need to be checked if it is a match to any customer in the
system. If custArray[index].getCustID().equals(inputID) , then that index will be used to print policies that
are linked to that customer. Customer then need to enter the policy ID of the policy he/she wishes to change.
Once the policy is selected, customer can choose to change cover value, duration, installment amount and payment
plan. For example, if cover value is to be changed, the program will ask customer to input new value. The 
newValue will be updated to that policy; cust[0].policy[1].setCoverValue(newValue) which is a setter method in
Policy class. Customers can also delete their policies. When policy is instantiated, the default value of the 
status of that policy is "Active". Deleting the policy will change the status to a newStatus="Deleted", 
cust[0].policy[1].setStatus(newStatus).
	The program also has the option to calculate the total cover values of all the policies of all the customers. A
variable total is set to 0 in the beginning. For loop is used to scan all the customers and inside that for loop, 
there is another for loop to get the the cover values of each and every policy linked to that customer. Inside the 
inner for loop, total=total+cust[i].policy[j].getCoverValue(). Therefore the total is updated for each and every
policy of each and every customer.
*/
import java.util.*;

public class Qn_B {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		//for user input
		Customer[] cust = new Customer[7];
		boolean breakLoop=false;
		int choice=0, count=0;
		do {
		printMenu();
		choice=input.nextInt();
		input.nextLine();
		switch(choice) {
		case 1:
			if(count<7) {
				System.out.print("Enter Customer Details\nCustomer Name: ");
				String name=input.nextLine();
				System.out.print("Address: ");
				String address=input.nextLine();
				cust[count] = new Customer(name, address);
				System.out.println("Customer created successfully!\nYour Customer ID: "+cust[count].getCustID());
				count++;
			}
			else {
				System.out.println("Maximum customer count reached.");					
			}
			break;
		case 2:
			if(count>0) {
				System.out.print("Enter Customer ID to Add a new Policy\nCustomer ID: ");
				String searchID=input.nextLine();
				int index=searchCustID(searchID, cust, count);
				if(index!=-1) {
					int addMorePol=1;
					while(cust[index].getCustPolCount()<5 && addMorePol==1) {
						System.out.println("Enter New Policy Details");
						int choice2=0;
						String type=null;
						while(breakLoop==false) {
							System.out.print("Type: 1-Vehicle.. 2-Health.. 3-Travel.. 4-Property.. 5-Pet (Enter number 1-5): ");
							choice2=input.nextInt();
							if(choice2<1 || choice2>5) {System.out.println("Invalid user input. Please enter 1-5."); }
							else { type=chooseCoverType(choice2); breakLoop=true; }	
						}
						breakLoop=false;	//reset breakLoop for next use
						double coverValue=0;
						while(breakLoop==false) {
							System.out.print("Cover Value: $");
							coverValue=input.nextDouble();
							if(coverValue<0) {System.out.println("Cover Value cannot be negative. Please re-enter.");}
							else {breakLoop=true;}
						}
						input.nextLine();
						System.out.print("Cover Duration (month): ");
						String duration=input.nextLine();
						breakLoop=false;	//reset breakLoop
						double installmentAmt=0;
						while(breakLoop==false) {
							System.out.print("Installment Amount: ");
							installmentAmt=input.nextDouble();
							if(installmentAmt<0) {System.out.println("Installment amount cannot be negative. Please re-enter.");}
							else {breakLoop=true;}
						}
						input.nextLine();
						breakLoop=false;	//reset breakLoop
						String plan=null;
						while(breakLoop==false) {
							System.out.print("Payment Plan: 1-Fortnight.. 2-Monthly.. 3-Yearly (Enter number 1-3): ");
							choice2=input.nextInt();
							if(choice2<1 || choice2>3) {System.out.println("Invalid user input. Please enter 1-3.");}
							else { plan=choosePaymentPlan(choice2); breakLoop=true;}
						}
						input.nextLine();
						System.out.print("Policy Start Date: ");
						String date=input.nextLine();
						int custPolCount=cust[index].getCustPolCount();
						Policy pol = new Policy(type, coverValue, duration, installmentAmt, plan, date);
						cust[index].addPol(pol);
						System.out.println("Policy Added! The Policy ID: "+cust[index].policy[custPolCount].getPolID());
						breakLoop=false;	//reset breakLoop
						System.out.println("Enter 1 - To add one more policy to the customer OR press any other number to go back to main menu.");
						addMorePol=input.nextInt();
					}
					if(addMorePol!=1) {break;}
					System.out.println("Maximum policy count reached for the customer.");
					break;
				}
				else{ System.out.println("Invalid Customer ID");}
			}
			else { System.out.println("There are no customers yet. Please add a customer."); }
			break;
		case 3:
			if(count>0) { 
				for(int i=0; i<count; i++) {
				printCust(cust[i]);
			}
			}else { System.out.println("There are no customers yet. Please add a customer."); }
			break;
		case 4:
			if(count>0) {
				System.out.print("Search by: 1-Customer ID.. 2-Name.. 3-Policy ID (Enter number 1-3): ");
				int choice4=input.nextInt();
				input.nextLine();
				String search=null;
				int index=0;
				switch(choice4) {
				case 1:
					System.out.print("Enter Customer ID to Search: ");
					search=input.nextLine();
					index=searchCustID(search, cust, count);
					if(index==-1) {System.out.println("Invalid Customer ID"); break;};
					printCust(cust[index]);
					break;
				case 2:
					System.out.print("Enter Customer Name to Search: ");
					search=input.nextLine();
					index=searchName(search, cust, count);
					if(index==-1) {System.out.println("Invalid Customer Name"); break;};
					printCust(cust[index]);
					break;
				case 3:
					System.out.print("Enter Policy ID to Search: ");
					search=input.nextLine();
					int policyIndex=0;
					for(int i=0; i<count; i++) {
						if(cust[i].getCustPolCount()!=0) {
							policyIndex=searchPolID(search, cust[i], cust[i].getCustPolCount());
						}
						if(policyIndex!=-1) { index=i; break; }
					}
					if(policyIndex==-1) {System.out.println("Invalid Policy ID"); break;};
					System.out.println(cust[index]);
					System.out.println(cust[index].policy[policyIndex]);
					break;
				default:
					System.out.println("Invalid user input. Please enter 1-3.");
					break;
				}
			}else { System.out.println("There are no customers yet. Please add a customer."); }
			break;
		case 5:
			if(count>0) { 
				System.out.print("Enter Customer ID to make changes: ");
				String search=input.nextLine();
				int index=searchCustID(search, cust, count);
				if(index==-1) {System.out.println("Invalid Customer ID"); break;};
				printCust(cust[index]);
				System.out.print("Enter Policy ID to make changes: ");
				String searchPolID=input.nextLine();
				int policyIndex=searchPolID(searchPolID, cust[index], cust[index].getCustPolCount());
				if(policyIndex==-1) { System.out.println("Invalid Policy ID"); break; }
				System.out.println("1-Change cover value\n2-Change duration\n3-Change installment amount\n4-Change Payment plan\n5-Delete policy\nEnter choice(Enter number 1-5): ");
				int choice5=input.nextInt();
				input.nextLine();
				makeChanges(choice5, cust[index], policyIndex, input);
			}else { System.out.println("There are no customers yet. Please add a customer."); }
			break;
		case 6:
			if(count>0) { 
				double total=totalCoverValue(cust, count);
				System.out.println("The Total Cover Value: $"+total);
			}else { System.out.println("There are no customers yet. Please add a customer."); }
			break;
		case 7:
			System.out.println("Exiting the program.");
			break;
		default:
			System.out.println("Invalid user input, please choose between 1-7.");
		}
		}while(choice!=7);
		input.close();
	}
	
	//print menu method
	public static void printMenu() {
		System.out.println("\n1-Add new Customer\n2-Add Policy\n3-Print All Customer\n4-Search Customer\n5-Make Changes\n6-Total Cover Value\n7-Exit");
	}
	
	//print customer
	public static void printCust(Customer cust) {
		System.out.println(cust);
		if(cust.getCustPolCount()>0) {
			for(int j=0; j<cust.getCustPolCount(); j++) {
				System.out.println(cust.policy[j]);
			}
			System.out.println("");
		}else { System.out.println(""); }
	}
	
	//search cust ID
	public static int searchCustID(String inputID, Customer[] custArray, int size) {
		int index=0;
		for(int i=0; i<size; i++) {
			if(custArray[i].getCustID().equals(inputID)) { index=i; break;}	//if ID found, return the index number where the ID is found
			else {index= -1;}	//if not found, return -1
		}
		return index;
	}
	//search cust name
	public static int searchName(String inputName, Customer[] custArray, int size) {
		int index=0;
		for(int i=0; i<size; i++) {
			if(custArray[i].getName().equals(inputName)) { index=i; break;}	//if name found, return the index number where the name is found
			else {index= -1;}	//if not found, return -1
		}
		return index;
	}
	//search policy no.
	public static int searchPolID(String inputID, Customer cust, int size) {
		int index=0;
		for(int j=0; j<cust.getCustPolCount(); j++) {
			if((cust.policy[j].getPolID()).equals(inputID)) { index=j; break;}	//if ID found, return the index where the policy ID is found
			else {index= -1;}	//if not found, return -1
		}
		return index;
	}
	
	//policy cover type
	public static String chooseCoverType(int choice) {
		String type=null;
		switch(choice) {
		case 1: { type="Vehicle"; break; }
		case 2: { type="Health"; break; }
		case 3: { type="Travel"; break; }
		case 4: { type="Property"; break; }
		case 5: { type="Pet"; break; }
		}return type;
	}
	//policy payment plan
	public static String choosePaymentPlan(int choice) {
		String plan=null;
		switch(choice) {
		case 1: { plan="Fortnight"; break; }
		case 2: { plan="Monthly"; break; }
		case 3: { plan="Yearly"; break; }
		}return plan; 
	}
	
	//make changes
	public static void makeChanges(int choice, Customer cust, int policyIndex, Scanner input){
		switch(choice) {
		case 1:
			boolean breakLoop=false;
			double newValue=0;
			while(breakLoop==false) {
				System.out.print("Enter new Cover Value: $");
				newValue=input.nextDouble();
				if(newValue<0) {System.out.println("Cover Value cannot be negative. Please re-enter.");}
				else {breakLoop=true;}
			}
			cust.policy[policyIndex].setCoverValue(newValue);
			break;
		case 2:
			System.out.print("Enter new duration: ");
			String newDuration=input.nextLine();
			cust.policy[policyIndex].setDuration(newDuration);
			break;
		case 3:
			System.out.print("Enter new Installment amount: $");
			double newAmount=input.nextDouble();
			cust.policy[policyIndex].setInstallmentAmt(newAmount);
			break;
		case 4:
			System.out.println("Enter new Payment Plan: ");
			System.out.print("Payment Plan: 1-Fortnight.. 2-Monthly.. 3-Yearly (Enter number 1-3): ");
			int newPlanChoice=input.nextInt();
			if(newPlanChoice<1 || newPlanChoice>3) {
				System.out.println("Invalid user input. Please enter 1-3.");
			}
			String newPlan=choosePaymentPlan(newPlanChoice);
			cust.policy[policyIndex].setPaymentPlan(newPlan);
			break;
		case 5:
			String newStatus="Deleted";
			cust.policy[policyIndex].setStatus(newStatus);
			break;
		default:
			System.out.println("Invalid user input. Please enter 1-5.");
			break;
		}
	}
	
	//total cover value
	public static double totalCoverValue(Customer[] cust, int size) {
		double total=0;
		for(int i=0; i<size; i++) {
			for(int j=0; j<cust[i].getCustPolCount(); j++) {
				if((cust[i].policy[j].getStatus()).equals("Active")) {
					total=total+cust[i].policy[j].getCoverValue();					
				}
			}
		}
		return total;
	}
}
