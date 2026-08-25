//Priscella Maenar 104511548 - COS10033 Assignment 1 Qn A

/*	The program is a movie theater seat booking program. The main menu prompt has some options for user to 
choose from. User will input their choice, which will then be used in a switch case for the menu. 
Do while loop is used for the main menu, until users choose exit, then the loop will end. Seats in the 
theater used 2d array, seats[8][10], the outer array (8) represents row while inner array (10) the column. 
	Users can choose to book seats manually or be assigned seats automatically by the system. the program will
check if the seats[row][col]==0 (0 means empty, 1 means taken). If seats are taken, users will not successfully
book the seats, if empty, seats are booked and a ticket will be issued. Ticket number starts from 101 and 
increments by 1 each time a new ticket is issued, static int variable in Ticket class is used for that. 
	With automatic seats booking, the program will allocate the next available seats that can fit the number of
seats requested by user. For loop is used for the outer seats array and in that, another for loop is used for
the inner seats array. This is to check for empty seats in the all the columns in the rows, and stop when seats
are found or when it reaches the end of the loop, then it means no seats available for the user. 
	Users can choose to see seat map for availability of seats. If condition is used for if seats[row][col]==1, 
print "X", else print "O". Those conditions are placed in a loop to scan all the rows and columns so the
program can print all the seats in the theater. 
	The print all the tickets option used for loop to print all the tickets in the Ticket array (Ticket[]).
Inside the loop, the toString() method in the Ticket class is used. 
	When searching for ticket ID to print ticket, the user input ID to search is check, in a for loop, whether 
it is equal to the ticketarray[index].getID(). getID() is used to access the private variable ticketID in the
Ticket class. If the ticket ID matched the search ID, the index will be returned and used to print the ticket.
	Lastly, the program also tells user how many seats are left. seatsAvail is updated using seatsAvailble 
function. the function updates seats available quantity by using seatsAvail=seatsAvail-qty. Originally 
seatsAvail=80, the qty is the number of seats successfully booked. Each time seats are successfully booked, 
the function is called so number of seatsAvail is always updated. */

import java.util.*;

public class Qn_A {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		//for user input
		Ticket[] tic = new Ticket[80];
		int[][] seats = new int[8][10];
		int choice=0, i=0, seatsAvail=80, ticketCount=0;
//		String seatsAlloc;
		do {
		printMenu();
		choice=input.nextInt();
		input.nextLine();
		switch(choice) {
		case 1: if(seatsAvail>0) {
			System.out.println("Enter how many tickets you wish to reserve?");
			int qty = input.nextInt();
			input.nextLine();
			if(qty<=10) {
				System.out.println("Do you wish the system to allocate the seats for you[y/n]?");
				String choice2 = input.nextLine();
				if(choice2.equals("n")) {
					System.out.println("Please select the row and seat number that you wish to reserve yout seats from:");
					String[] seatChoice = input.nextLine().split("");
					String seatRow = seatChoice[0];		//user input seat row
					int seatCol=0;
					//if the user input seat col is 10 
					if(Integer.parseInt(seatChoice[1])==1 && seatChoice.length==3) {
						String seatColString = seatChoice[1]+seatChoice[2];
						seatCol = Integer.parseInt(seatColString);	//convert user input seat col from string to int
					}else {		//if user input seal col is less than 10
						seatCol = Integer.parseInt(seatChoice[1]);								
					}
					if(seatCol-1+qty>10){		//if the the seats exceed the available seats in the row
						System.out.println("Sorry, no allocation can be made. Insufficient seats in the row."); break;
					} else {
						if(manualBooking(seats, rowNum(seatRow), seatCol-1, qty)==true) {
							bookSeats(seats, rowNum(seatRow), seatCol-1, qty);
						}
						else {System.out.println("Sorry, no allocation can be made. Seats already taken."); break;}
					}
					System.out.println("Seats reserved: "+seatsReserved(seatRow,seatCol,qty));
					tic[i]=new Ticket(qty, seatsReserved(seatRow,seatCol,qty));
					System.out.println(tic[i]);
					i++; ticketCount++;
					seatsAvail=seatsAvailable (seatsAvail, qty);
					printSeats(seats);
				} //end of choice2=n
				else if(choice2.equals("y")) {
					int row=0, col=0, count=0, colIndex=0, br=0;
					for(row=0; row<seats.length; row++) {
						for(col=0; col<seats[row].length; col++) {
							if(seats[row][col]==0) { 
								count++;
								if(count==qty) {  //when count=qty means no. of seats requested by user are next to one another.
									colIndex=col+1-qty;  //colIndex is the end of seat col range based on the no. of seats requested by user
									br=1; 	//br=1, to break out of the outer loop
									break;
								}
								//if not enough seats found after scanning all the row and all the col.
								else if(count<qty && row==seats.length-1 && col==seats[row].length-1) {
									br=2; break;
								}
							} else { count=0; }
						}
						if(br==1) {
							if(col>=0 && colIndex<0) {	//means not enough empty seat in the previous row so system goes to the next row
								count=0;	//reset the count in that next row, so system can search seats in that row
								colIndex=0;
								row--; //reset row so system can scan seats in that row again
								br=0;  //reset break condition
							}else {break;}
						}
					}
					if(br==2) {System.out.println("Sorry, could not find a row with enough seats to be all next to one another.");}
					else {
						bookSeats(seats, row, colIndex, qty);
						System.out.println("Seats reserved: "+seatsReserved(rowAlphabet(row),colIndex+1,qty));
						tic[i]=new Ticket(qty, seatsReserved(rowAlphabet(row),colIndex+1,qty));
						System.out.println(tic[i]);
						i++; ticketCount++;
						seatsAvail=seatsAvailable (seatsAvail, qty);
						printSeats(seats);
					}
					break;
				}//end of choice2=y
				else { System.out.println("Invalid input. Please enter y/n"); }
			} else { System.out.println("Sorry, maximum 10 tickets can be reserved at a time."); }
		} else { System.out.println("No more seats available."); }
			break;
		case 2:
			printSeats(seats);
			break;
		case 3:
			System.out.println(seatsAvail+" seats are available for reservation.");
			break;
		case 4:
			if(ticketCount>0) {
				System.out.print("Enter Ticket #: ");
				int search = input.nextInt();
				int index=searchTicket(search, tic, ticketCount);	//return the index no. or -1
				if(index!=-1) { System.out.println(tic[index]); }	//if found (index not= -1), use that index no. to print ticket details
				else { System.out.println("Ticket # not found"); }	//if index=-1, not found. 
			}
			else { System.out.println("No tickets have been sold."); }
			break;
		case 5:
			if(ticketCount>0) {
				for(i=0; i<ticketCount; i++) {
					System.out.println(tic[i]);
				}
			}
			else { System.out.println("No tickets have been sold."); }
			break;
		case 6:
			System.out.println("Exiting the program.");
			break;
		default:
			System.out.println("Invalid user input, please choose between 1-6.");
		}
		}while(choice!=6);
		input.close();
	}

	public static void printMenu() {		//print menu method
		System.out.println("\n1 - Reserve Tickets\n2 - Show Current Availability\n3 - Show Count of Availability\n4 - Search Ticket\n5 - Print All Tickets\n6 - Exit");
	}
	
	//print seat map
	public static void printSeats(int[][] seats) {
		System.out.println("\n---------SCREEN--------\n");
		System.out.println("   1 2 3 4 5 6 7 8 9 10");
		for(int row=0; row<seats.length; row++) {
			System.out.print(rowAlphabet(row)+"  ");
			for(int col=0; col<seats[row].length; col++) {
				if(seats[row][col]==1)
					System.out.print("X ");
				else
					System.out.print("O ");
			}
			System.out.println();
		}
		System.out.println("  O-available  X-taken");
	}
	
	//to change row alphabet to number
	public static int rowNum(String alphabet) {
		int num=0;
		switch(alphabet) {
		case "A": {num= 0; break;}
		case "B": {num= 1; break;}
		case "C": {num= 2; break;}
		case "D": {num= 3; break;}
		case "E": {num= 4; break;}
		case "F": {num= 5; break;}
		case "G": {num= 6; break;}
		case "H": {num= 7; break;}
		}return num;
	}
	//to change row number to alphabet
	public static String rowAlphabet(int num) {
		String alphabet=null;
		switch(num) {
		case 0: {alphabet="A"; break;}
		case 1: {alphabet="B"; break;}
		case 2: {alphabet="C"; break;}
		case 3: {alphabet="D"; break;}
		case 4: {alphabet="E"; break;}
		case 5: {alphabet="F"; break;}
		case 6: {alphabet="G"; break;}
		case 7: {alphabet="H"; break;}
		} return alphabet;
	}
	
	//calculate how many seats are available
	public static int seatsAvailable (int availQty, int qty) {
		return availQty-qty;
	}
	
	//search ticket
	public static int searchTicket(int inputID, Ticket[] ticketArray, int size) {
		int index=0, i=0;
		for(i=0; i<size; i++) {
			if((ticketArray[i].getID())==inputID) { index=i; break;}	//if ID found, return the index number of that ticket index
			else {index= -1;}	//if not found, return -1
		}
		return index;
	}
	
	//Covert seats seats booked to string
	public static String seatsReserved(String row, int col, int qty) {
		int ColEnd=col-1+qty;		//end of booked seats range
		String colStart=Integer.toString(col); 	//start of booked seats range
		String colEnd=Integer.toString(ColEnd);		//convert ColEnd to String
		return (row+colStart+"-"+row+colEnd);
	}
	
	//check seats when user choose own seats
	public static boolean manualBooking(int[][] seatsArray, int row, int col, int qty) {
		boolean check=true;
		for(int i=0; i<qty; i++) {
			if(seatsArray[row][col+i]==0) { check=true; }
			else { check=false; break;}
		}
		return check;
	}
	
	//book seats
	public static void bookSeats(int[][] seatsArray, int row, int col, int qty) {
		for(int j=0; j<qty; j++) {
			seatsArray[row][col]=1;
			col++;
		}
	}
}