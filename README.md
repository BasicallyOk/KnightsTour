# KnightsTour Solver
A program that solves the knight's tour problem.
A board size can be specified within the code. 
There are 3 distinct approaches the program can take: 
- Backtracking (slow, not recommended)
- Randomized pathfinding: The program randomly choose a location to go to using a list of location and their weights (how likely should they be chosen, corresponds to Warnsdorff's rule), currently the fastest and recommended approach. However, since the algorithm does implement randomization, the amount of time used each time varies (64x64 takes anywhere from 50 to 200ms)
- Divide & Conquer: Divides the board into smaller, equal boards (because of this it will only work on boards with size 2nx2n although improvements can be made for it to support others) and implements Randomized Pathfinding on the smallest possible partition. Because of this, the program highly favors boards of the form i*2^nxj*2^n (ixj must be solvable), solving a 1024x1024 board in 150 milliseconds. 
