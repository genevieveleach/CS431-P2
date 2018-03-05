# Virtual-Memory-Simulator
Virtual Memory Simulator for Operating Systems Winter 2018

This program is designed to simulate a virtual memory. The program needs several classes to run. The test files and pages should be stored in different folders along with the classes. You will need to make sure you have a folder for src, test files, and page files. The page files should be .pg files and the test files must be .txt files. In the src folder you will need CPU.java, Driver.java, OS.java, PageTableEntry.java, PhysicalMemory.java, TLBEntry.java, and VirtualPageTable.java.

Once you have made sure that all the files are in their proper place, you will need to type the following commands into the command line to run the code:
1. javac Driver.java
2. java Driver [test filename] (ex. java Driver test_1.txt)

The console will print any values read from the file and the program will create an output file called output.csv that will record the Address, R/W, Value, Soft Miss, Hard Miss, Hit, Evicted_Pg#, and Dirty_Evicted_Page separated by commas.

The output file will be formatted incorrectly if opened in excel but should be fine if opened in notepad or wordpad.
