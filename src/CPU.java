import java.util.*;
import java.io.*;

public class CPU {
  public static void readFile(Scanner input) {
    while (input.hasNextLine()) {
      String line = input.nextLine();
      String name = line.substring(0,2);
      String offset = line.substring(2,4);
      int addr = Integer.parseInt(name, 16);
      int off = Integer.parseInt(offset, 16);
      int instCount = 0;
      if (instCount / 20 == 0) {
        OS.resetR();
        instCount = 0;
      }
      /*- process and pass to mmu
      if (valid and Read){
        Physicalmem.Read(PF#, offset#);
      }
      else if (Valid){
        pm.write(PF#, offset, data);
      }
      else{
        OS.pull(PT#);
      }
      addr = 
      System.out.println(addr + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
      counter++;*/
    }
  }

  public class MMU {
    //if 1 write, if 0 read
    int rw;
    int address;
    int data;

    public int getData(int rw, int address, int data) {
      this.rw = rw;
      this.address = address;
      this.data = data;

      //rw == 0, read
      if (rw == 0) {
        if (checkTLB(address) > -1) {
          //if valid
        } else if (checkPageTable(address) > -1) {
          //if valid
        } else {
          //what else is here? i didn't see in the skeleton
        }
      } else {
        //rw == 1, write
        //TODO:
      }
      return pageFrameNum;
    }

    private int checkPageTable(int address) {
      //TODO:
      return -1;
    }

    private int checkTLB(int address) {
      for(int i = 0; i < 8; i++) {
        if(TLB[i].getVirtualPageNum() == address) {
          return i;
        }
      }
      return -1;
    }
  }
  /*Class MMU{
    int R/W;
    int address; //what does this store?
    int data;
    public static void ReplaceTLBEntry(){
    }
    public static int getData(int, int, int){
      - Sets 3 ints
      if (check.TLB() > -1){// if valid
      }
      else if (check.Pagetable() > -1){// if valid
        h/m/s is set
      }
      if (r/w == 1){
        set d = 1
      }
      return page frame #
    }
  }*/
}
