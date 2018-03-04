import java.util.*;
import java.io.*;

public class CPU {
  private TLBEntry[] TLB = new TLBEntry[8];
  private VirtualPageTable vPT;
  private PhysicalMemory PM;
  public CPU(){
    for (int i=0; i<8; i++){
      TLB[i] = new TLBEntry();
    }
    vPT= new VirtualPageTable();
  }
  public static void readFile(Scanner input) {
    while (input.hasNextLine()) {
      String line = input.nextLine();
      int addr = Integer.parseInt(line, 16);
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


  private class MMU {
    //if 1 write, if 0 read
    int rw;
    int address;
    int data;
    int TLBPointer;

    //init pointer to 0 and make TLB a list of empty slots
    public MMU(){
      TLBPointer = 0;
    }

    public int getData(int rw, int address, int data) {
      this.rw = rw;
      this.address = address;
      this.data = data;

      int vPage = this.getVPageNum(address);
      int pageFrameNum = getPageFrameNum( vPage );

      //read or write is decided
      setRW(vPage, rw);
      if (rw == 1){   //write
        //im not sure who writes to physical mem, so move this as needed
        PM.setPhysicalMem(pageFrameNum, this.getOffset(address), data);
      }
      else{   //read
        System.out.println(PM.getPhysicalMem(pageFrameNum, this.getOffset(address)));
      }

      return 0;
    }

    private void setRW(int address, int rw){
      for (int i=0; i< 8; i++){
        if (TLB[i].getvPageNum() == address){
          TLB[i].setR(1);
          vPT.setR(address,1);
          if(rw == 1) {
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
      if (vPT.getV(address) == 1){    //if adress is valid...
        vPT.setR(address, 1); // sets R bit to 1
        return vPT.getPageFrameNum(address);  //returns page frame number
      }
      return -1;
    }
    private int checkTLB(int address) {
      for(int i = 0; i < 8; i++) {
        if(TLB[i].getvPageNum() == address) { return i; }
      }
      return -1;
    }
  }


  /*
  Class MMU{
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
  }
  */
}
