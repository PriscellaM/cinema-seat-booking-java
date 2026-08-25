# Cinema Seat Booking System (Java)

**Course Project:** COS10033 - Assignment 1  

---

## Overview

A menu-driven Java console application designed to manage ticket bookings and seat allocations for a cinema. The system models an 8x10 seating grid (80 seats total) using a 2D array and tracks tickets dynamically as reservations are made.

---

## Key Features

* **Manual Seat Selection:** Select a preferred row (A-H) and starting column (1-10) for consecutive seat reservations.
* **Automatic Seat Allocation:** Scans the theater row-by-row to automatically reserve consecutive available seats for the requested quantity.
* **Interactive Visual Map:** Displays a real-time layout of the theater showing available (O) and reserved (X) seats.
* **Ticket Management:**
  * Issues unique ticket IDs starting at 101.
  * Allows searching for active tickets by ID.
  * Lists details for all generated tickets.
* **Real-time Capacity Tracking:** Continuously updates and reports remaining seat availability.

---

## Technical Details

* **Language:** Java
* **Data Structures:** 
  * `int[8][10]` 2D array representing theater rows (A-H) and columns (1-10).
  * `Ticket[]` array for storing booking records.
* **Control Flow:** `do-while` menu loop coupled with a `switch-case` construct for user input navigation.

---

## Program Menu Options

1. **Reserve Tickets**: Book seats manually or automatically (up to 10 tickets per transaction).
2. **Show Current Availability**: Print the visual seating chart.
3. **Show Count of Availability**: Display remaining seat count.
4. **Search Ticket**: Find ticket details using a unique Ticket ID.
5. **Print All Tickets**: Display all issued tickets.
6. **Exit**: Terminate the program.
