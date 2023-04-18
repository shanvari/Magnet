# Magnet Puzzle
In this game, you are given a table with numbers specified for its rows and columns
Is. Your goal is to fill the table by placing a number of magnets in the table in such a way that
The number of positive and negative poles in each row and column is equal to the numbers written for that row and column
(The first row above the table shows the number of positive poles and the second row shows the number of negative poles
and the same condition exists for the numbers on the left side of the table). Note that like the world
Actually, poles of the same name cannot be adjacent to each other.

![Screenshot (50)](https://user-images.githubusercontent.com/45328431/232902526-1025d8d2-c498-4b6f-9382-b0419119e35a.png)
# Implementation
First, model the problem as a constraint satisfaction problem. Then using the algorithm
 The problem of LCV and MRV and Forward Checking heuristics and the use of Backtracking
solve Then check the effect of using Arc Consistency (implement AC3 algorithm
and check what adding it helps solve).

# input and output
* In the first line of input, the number of rows and columns
* In the second input line, the number of positive poles for each line
* In the third input line, the number of negative poles for each line
* In the fourth line, enter the number of positive poles for each column
* In the fifth line, enter the number of negative poles for each column
Then the table is given. We use two methods to specify vertical and horizontal houses, and you are allowed to use one of these methods as you wish.
2 First method: each pair of vertical houses is displayed with the number one and each pair of horizontal houses is displayed with the number zero.
The second method: We display each pair of houses with two identical natural numbers.
To display the output, fill the table with compasses and leave zeros in the empty spaces
