## CS 4375 Homework 1

This is our code for CS 4375 Homework 1: Decision Trees, created by Trent Davies and Chi Kan Cheung

## How To

Our code contains two parts, first to train the tree, and secondly to test data on the tree

It's important to train the tree first in order to generate it, then run the processor with the generated tree.

0. Compile the code using 

javac DecisionTreeMain.java
javac DecisionTreeProcessor.java

1. Run DecisionTreeMain with an argument that contains the training data file name that the tree will be trained on.
	EX: java DecisionTreeMain "train.dat"

2. Run DecisionTreeProcessor with two arguments, the training data file name and the testing data file name that the tree will be tested on.
	EX: java DecisionTreeProcessor "train.dat" "test.dat"