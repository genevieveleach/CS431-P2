import java.io.IOException;
import java.util.Scanner;

public class CPU {
    //variables
    private static TLBEntry[] TLB = new TLBEntry[8];
    public static VirtualPageTable vPT;
    public static PhysicalMemory PM;

    public CPU(){
        for (int i=0; i<8; i++){
            TLB[i] = new TLBEntry();
        }
        vPT = new VirtualPageTable();
        PM = new PhysicalMemory();
    }

    static void readFile(Scanner input) throws IOException {
        int instCount = 0;
        while (input.hasNextLine()) {
            String rewr = input.nextLine();
            int rw = Integer.parseInt(rewr);
            Driver.rw = rw;
            String line = input.nextLine();
            Driver.addr = line;
            //System.out.println("Broken" + Driver.addr); //dbug
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
            Driver.writeDataToFile();
            instCount++;
        }
    }

    public static class MMU {
        //if 1 write, if 0 read
        static int rw;
        static String address;
        static int data;
        static int TLBPointer = 0;

        public MMU() { TLBPointer = 0; }

        static int getData(int rewr, String addr, int datas) throws IOException {
            rw = rewr;
            address = addr;
            data = datas;

            int vPage = Integer.parseInt(address.substring(0,2), 16);        // this is the first 2 hex nums of [1A][2B]
            int pageFrameNum = getPageFrameNum( vPage );    //this is the first part of [A][BC]
            int offset = Integer.parseInt(address.substring(2,4), 16);

            //read or write is decided
            setRW(vPage, rw);
            if (rw == 1) {   //write
                PM.setPhysicalMem(pageFrameNum, offset, data);
                Driver.value = Integer.toString(data);
                System.out.println(Integer.toString(data));
            }
            else {   //read
                System.out.println(PM.getPhysicalMem(pageFrameNum, offset));
                Driver.value = Double.toString(PM.getPhysicalMem(pageFrameNum, offset));
            }
            return 0;
        }

        private static void setRW(int address, int rw){
            for (int i=0; i< 8; i++) {
                if (TLB[i].getvPageNum() == address && TLB[i].getV() == 1) {
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

        private static int getPageFrameNum(int address) throws IOException{
            //initial TLB check
            int pageFrameNum = checkTLB(address);
            //System.out.println("TLB" + pageFrameNum);
            if (pageFrameNum == -1){  //miss in TLB
                pageFrameNum = checkPageTable(address);
                //System.out.println("VPT" + pageFrameNum);
                if (pageFrameNum == -1){  //hard miss
                    Driver.hard = 1;
                    Driver.soft = 0;
                    Driver.hit = 0;
                    pageFrameNum = OS.hardMiss(address, vPT);
                    //System.out.println(pageFrameNum + "");
                    vPT.setPageFrameNum(address, pageFrameNum);
                    vPT.setR(address, 1);
                    vPT.setV(address, 1);
                    vPT.setD(address, 0);
                }
                else{   //soft miss
                    //nothing "logic" code happens here, just here to write that a soft miss happened
                    Driver.soft = 1;
                    Driver.hard = 0;
                    Driver.hit = 0;
                    Driver.evicted = "N/A";
                    Driver.dirty = -1;
                }
                //new TLB entry due to miss
                newPageTableEntry(pageFrameNum, address);
            }
            else{ //hit
                //pageFrameNum = TLB[pageFrameNum].getPageFrameNum();
                Driver.hit = 1;
                Driver.soft = 0;
                Driver.hard = 0;
                Driver.evicted = "N/A";
                Driver.dirty = -1;
            }
            //System.out.println(pageFrameNum + " check"); //debug
            return pageFrameNum;
        }
        private static void newPageTableEntry(int pFrameNum, int address){
            TLBEntry temp = new TLBEntry(pFrameNum, address);
            temp.setV(1);
            TLB[TLBPointer] = temp;
            TLBPointer = (TLBPointer + 1) % 8;
            //System.out.println("tlb pointer: " + TLBPointer);
        }
        private static int checkPageTable(int address) {
            if( vPT.getPageFrameNum(address) == -1 ) {
                //System.out.println("-1");
                return -1;
            }
            else {
                //System.out.println("00000");
                return vPT.getPageFrameNum(address);
            }
        }
        private static int checkTLB(int address) {
            for(int i = 0; i < 8; i++) {
                if(TLB[i].getvPageNum() == address && TLB[i].getV() == 1)
                    return TLB[i].getPageFrameNum();
            }
            return -1;
        }
    }
}
