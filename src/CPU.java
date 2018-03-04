import java.util.*;
import java.io.*;

public class CPU {
  //variables
  private TLBEntry[] TLB = new TLBEntry[8];
  private VirtualPageTable vPT;
  private PhysicalMemory PM;
  public CPU(){
    for (int i=0; i<8; i++){
      TLB[i] = new TLBEntry();
    }
    vPT = new VirtualPageTable();
  }

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
        MMU.getData(rw, addr, 0);
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
        String d = input.nextLine();
        int da = Integer.parseInt(d);
        if (instCount / 20 == 0) {
          OS.resetR();
          instCount = 0;
        }
        MMU.getData(rw, addr, da);
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
      else {
        System.out.println("Error when parsing file");
        System.exit(0);
      }
      System.out.println(addr + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
      instCount++;
    }
  }

  public class MMU {
    //if 1 write, if 0 read
    int rw;
    int address;
    int data;
    int TLBPointer;

    public MMU() { TLBPointer = 0; }
    public int getData(int rw, int address, int data) {
      this.rw = rw;
      this.address = address;
      this.data = data;

      int vPage = this.getVPageNum(address);          // this is the first 2 hex nums of [1A][2B]
      int pageFrameNum = getPageFrameNum( vPage );    //this is the first part of [A][BC]

      //read or write is decided
      setRW(vPage, rw);
      if (rw == 1){   //write
        //im not sure who writes to physical mem, so move this as needed
        PM.setPhysicalMem(pageFrameNum, this.getOffset(address), data);
        this.setD(vPage);
      }
      else{   //read
        System.out.println(PM.getPhysicalMem(pageFrameNum, this.getOffset(address)));
      }

      return 0;
    }

    private void setD(int address){
      for(int i=0; i<8; i++){
        if (TLB[i].getvPageNum() == address) {
          TLB[i].setD(1);
          vPT.setD(address, 1);
          return;
        }
      }

    }
    private void setRW(int address, int rw){
      for (int i=0; i< 8; i++) {
        if (TLB[i].getvPageNum() == address) {
          TLB[i].setR(1);
          vPT.setR(address, 1);
          if (rw == 1) {
            TLB[i].setD(1);
            vPT.setD(address, 1);
          }
          return;
        }
      }
    }
    private int getOffset(int address){
      //TODO:
      //gets the offset
      return -1;
    }
    private int getVPageNum(int address){
      //TODO:
      // this is where we extract the first half of the address
      return -1;
    }
    private int getPageFrameNum(int address){
      //initial TBL check
      int pageFrameNum = this.checkTLB(address);
      if (pageFrameNum == -1){  //miss in TLB
        pageFrameNum = this.checkPageTable(address);
        if (pageFrameNum == -1){  //hard miss
          //TODO:
          //call OS to get data from files
          //store data in physical mem
          //return the "page" number
          //code for outpting to CSV file about evited page # && if the page was dirty goes here
        }
        else{   //soft miss
          //nothing "logic" code happens here, just here to write that a soft miss happened
        }
        //new TLB entry due to miss
        newPageTableEntry(pageFrameNum, address);
      }
      else{ //hit
        // nothing "logic" happens here either, only ment to write that a hit happened
      }
      return pageFrameNum;
    }
    private void newPageTableEntry(int pFrameNum, int address){
      TLBEntry temp = new TLBEntry(pFrameNum, address);
      temp.setV(1);
      TLB[TLBPointer] = temp;
      TLBPointer = (TLBPointer + 1) % 8;
    }
    private int checkPageTable(int address) {
      for (int i=0; i< 8; i++){
        if (TLB[i].getvPageNum() == address) return i;
      }
      return -1;
    }
    private int checkTLB(int address) {
      for(int i = 0; i < 8; i++) {
        if(TLB[i].getvPageNum() == address) return i;
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
