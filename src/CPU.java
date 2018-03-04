import java.util.Scanner;

import static com.sun.javafx.css.SizeUnits.PT;

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

    void readFile(Scanner input) {
        int instCount = 0;
        while (input.hasNextLine()) {
            String rewr = input.nextLine();
            int rw = Integer.parseInt(rewr);
            Driver.rw = rw;
            String line = input.nextLine();
            Driver.addr = line;
            if (rw == 0){
                if (instCount % 5 == 0) {
                    OS.resetR(TLB, vPT);
                    instCount = 0;
                }
                MMU.getData(rw, line, 0);
            } 
            else if (rw == 1){
                String d = input.nextLine();
                int da = Integer.parseInt(d);
                if (instCount % 5 == 0) {
                    OS.resetR(TLB, vPT);
                    instCount = 0;
                }
                MMU.getData(rw, line, da);
            }
            else {
                System.out.println("Error when parsing file");
                System.exit(0);
            }
            System.out.println(Driver.addr + "," + Driver.rw + "," + Driver.value + "," + Driver.soft + "," + Driver.hard + "," + Driver.hit + "," + Driver.evicted + "," + Driver.dirty);
            instCount++;
        }
    }

    public class MMU {
        //if 1 write, if 0 read
        int rw;
        String address;
        int data;
        int TLBPointer;

        public MMU() { TLBPointer = 0; }

        int getData(int rw, String address, int data) {
            this.rw = rw;
            this.address = address;
            this.data = data;

            int vPage = Integer.parseInt(address.substring(0,2), 16);        // this is the first 2 hex nums of [1A][2B]
            int pageFrameNum = getPageFrameNum( vPage );    //this is the first part of [A][BC]
            int offset = Integer.parseInt(address.substring(2,4), 16);

            //read or write is decided
            setRW(vPage, rw);
            if (rw == 1) {   //write
                PM.setPhysicalMem(pageFrameNum, offset, data);
                this.setD(vPage);
            }
            else {   //read
                System.out.println(PM.getPhysicalMem(pageFrameNum, offset));
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

        private int getPageFrameNum(int address){
            //initial TLB check
            int pageFrameNum = this.checkTLB(address);
            if (pageFrameNum == -1){  //miss in TLB
                pageFrameNum = this.checkPageTable(address);
                if (pageFrameNum == -1){  //hard miss
                    Driver.hard = 1;
                    //TODO:
                    //call OS to get data from files
                    //store data in physical mem
                    //return the "page" number
                    //code for outputting to CSV file about evited page # && if the page was dirty goes here

                    /*
                    remove comments later for when OS makes the function this needs
                    PLEASE DONT USE THE FUNCTION AS THE REASON FUNCTION NAME!! SERIOUSLY!
                    also removese these "real" comments~_~
                    pageFrameNum = OS_Function_That_Adds_Data_Into_Physical_Mem_And_Returns_The_Page_Number();
                     */
                }
                else{   //soft miss
                    //nothing "logic" code happens here, just here to write that a soft miss happened
                    Driver.soft = 1;
                }
                //new TLB entry due to miss
                newPageTableEntry(pageFrameNum, address);
            }
            else{ //hit
                // nothing "logic" happens here either, only ment to write that a hit happened
                Driver.hit = 1;
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
