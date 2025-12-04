# 🛒 Delivery Path Visualizer – Java Swing

This project is a grid-based delivery visualization tool built with **Java Swing**.  
It allows you to simulate delivery routes from multiple stores to multiple customers based on user-defined directions.

## 🚀 Features

- Grid-based interactive visualization  
- Multiple stores & customers  
- Support for tunnels (teleportation points)  
- Multi-line direction input:
S1 C1: down, right, up
S1 C2: tunnel, down
S2 C1: left, up, up
S2 C2: right, tunnel, right

pgsql
Copier le code
- Automatic rendering of **all paths** from all stores to customers  
- Arrow-based path drawing  
- Boundary checking (no moves outside the grid)

## 📂 Project Structure

/src
├── GridData.java # Grid, costs, stores, customers, tunnels
├── Position.java # Position + movement logic + boundary checks
├── WorkingDeliveryApp.java # Main Swing UI + grid rendering

markdown
Copier le code

## 🧭 How to Use

1. Run the application (`WorkingDeliveryApp`).
2. Enter all directions in the multiline text area:
S1 C1: down, right
S1 C2: tunnel
S2 C1: up, left
S2 C2: right, right, down

markdown
Copier le code
3. Click **Visualize** to display all paths.

## 🛠 Requirements

- Java 8+
- Any IDE (IntelliJ, NetBeans, Eclipse, VS Code)

## 📌 Notes

- Directions supported:  
`up`, `down`, `left`, `right`, `tunnel`  
- Paths are drawn with colored arrows.
- Costs matrices are correctly defined:
```java
H_COSTS = new int[ROWS][COLS - 1];
V_COSTS = new int[ROWS - 1][COLS];