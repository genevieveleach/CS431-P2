import java.util.*;
import java.io.*;

public class CPU {
  public static void readFile(Scanner input) {
    int instCount = 0;
    while (input.hasNextLine()) {
      String rewr = input.nextLine();
      int rw = Integer.parstInt(rewr);
      String line = input.nextLine();
      int addr = Integer.parseInt(line, 16);
      if (rw == 0){
        if (instCount / 20 == 0) {
        OS.resetR();
        instCount = 0;
        }
        - process and pass to mmu
        if (valid and Read){
          Physicalmem.Read(PF#, offset#);
        }
        else if (Valid){
          Physicalmem.write(PF#, offset, data);
        }
        else{
          OS.pull(PT#);
        }
      }
      else if (rw == 1){
        String data = input.nextLine();
        
      }
      else {
        System.out.println("Error when parsing file");
        System.exit(0);
      }
      addr = 
      System.out.println(addr + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
      instCount++;
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
