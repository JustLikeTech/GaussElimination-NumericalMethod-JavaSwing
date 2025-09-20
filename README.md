# Gauss & Gauss-Jordan Elimination with GUI (Java Swing)

This project is a **Java Swing application** that solves systems of linear equations using:  
- **Gauss Elimination Method**  
- **Gauss-Jordan Elimination Method**  

The application provides a graphical interface for entering a **3x3 system of linear equations** (augmented matrix) and displays the step-by-step solution.

## Features
- GUI-based input for a **3x4 augmented matrix** (3 equations with 3 variables).
- Dropdown to select solving method:
  - Gauss
  - Gauss-Jordan
- Displays:
  - The initial augmented matrix
  - Transformation steps (row operations)
  - Final results of variables \(x_1, x_2, x_3\).
- Clear step-by-step explanation in a text area.

## Methods Implemented
1. **Gauss Elimination**  
   Converts the augmented matrix into an upper triangular form, then applies **back substitution**.

2. **Gauss-Jordan Elimination**  
   Converts the augmented matrix into **reduced row echelon form (RREF)**, leading directly to the solution.

## How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/username/repo-name.git
