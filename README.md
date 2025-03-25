# Organizational Chart Management System
## Overview
This project is a Java-based organizational chart management system developed as part of the "Software Engineering" course at the University of Calabria. The application allows users to dynamically create, modify, and visualize hierarchical organizational structures with support for roles and employees. The system leverages several design patterns to ensure flexibility, maintainability, and scalability.

## Features
- **Dynamic Structure Updates:** Add, remove, or rename nodes in the organizational chart with real-time updates.

- **File Storage:** Save and load organizational charts using Java serialization.

- **Automated Layout:** Nodes are automatically positioned based on hierarchy using a Visitor pattern.

- **Role and Employee Management:** Assign roles and employees to nodes with inheritance support.

- **Customizable Styles:** Change the visual style of connecting lines between nodes.

- **User-Friendly Interface:** Intuitive GUI with contextual menus and shortcuts.

## Design Patterns Used
- **Composite:** Represents the hierarchical structure of the organizational chart.

- **Visitor:** Implements operations like node positioning and drawing without modifying node classes.

- **Abstract Factory:** Dynamically changes the drawing style of connecting lines.

- **Singleton:** Ensures unique instances of factories.

- **Command:** Manages node operations (add, remove, rename) in a clean and reusable way.

## Technical Details
- Language: Java

- Serialization: Java's built-in serialization for saving and loading organizational charts.

- GUI: Custom JFrame and JPanel implementations with Swing components.

- Data Structure: N-ary tree for representing the organizational hierarchy.

## Requirements Fulfillment
### Functional Requirements
- Dynamic Structure Updates: Achieved via the Composite pattern and real-time repainting.

- File Storage: Implemented using Java serialization to save/load the tree structure.

- Automated Layout: Visitor pattern ensures nodes are positioned correctly based on hierarchy.

- Customizable Styles: Abstract Factory pattern allows runtime changes to line drawing styles.

### Non-Functional Requirements
- Usability: Intuitive interface with help sections and shortcuts.

- Robustness: Handles edge cases with custom exceptions and error dialogs.

## Screenshots
![image](https://github.com/user-attachments/assets/30e90a7c-54ee-48ee-8b2a-330313df04dc)
![image](https://github.com/user-attachments/assets/1f2778e9-0f3e-4c3b-b3e0-1b163f0b795b)
![image](https://github.com/user-attachments/assets/c0082e03-566c-4b44-ac59-15c475d6a4b7)


## Installation
1. Clone the repository.

1. Ensure Java JDK is installed.

1. Compile and run Applicazione.java in /src/ using your preferred Java IDE or command line.

## Usage
- Create/Modify Nodes: Use contextual menus or shortcuts to add, remove, or rename nodes.

- Manage Roles/Employees: Right-click a node to assign roles or employees.

- Save/Load: Use the File menu to save or load organizational charts.

- Change Styles: Switch line styles via the settings menu.
