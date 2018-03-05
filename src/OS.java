import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class OS {
  private static int clockIndex;
  
  static void initPages() throws IOException {
      clockIndex = 0;
      String temp;
      for( int i = 0; i < 256; i++) {
          if(i < 16) {
            temp = "0" + Integer.toHexString(i).toUpperCase();
          }
          else {
            temp = Integer.toHexString(i).toUpperCase();
          }
          
          String src = "../page_files/original/" + temp + ".pg";
          String dest = "../page_files/copy/" + temp + ".pg";
          Files.copy(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
      }
  }

    //this function takes data from the file, then inputs it into physical memory
    private static void writeToPhysicalMem(int pageNum, int newOwnerOfPage) throws IOException {
        // open correct file from folder
        File inputPage = new File("../page_files/original/" + (pageNum < 16 ? "0":"") + Integer.toHexString(pageNum).toUpperCase() + ".pg");
        BufferedReader reader = new BufferedReader( new FileReader(inputPage) );
        setAllBits(newOwnerOfPage, pageNum);
        //input data from file into physical memory
        for( int i = 0; i < 256; i++) {
            int data = reader.read();
            CPU.PM.setPhysicalMem(pageNum, i, data);
        }
        reader.close();
    }


    private static void setAllBits(int newOwnerOfPage, int pageNum){
      for(int i=0; i< 8; i++)
          if( CPU.TLB[i].getvPageNum() == newOwnerOfPage){
              CPU.TLB[i].setV(1);
              CPU.TLB[i].setR(1);
              CPU.TLB[i].setD(0);
              CPU.TLB[i].setPageFrameNum(pageNum);
              CPU.TLB[i].setvPageNum(newOwnerOfPage);
          }
      CPU.vPT.setV(newOwnerOfPage, 1);
      CPU.vPT.setR(newOwnerOfPage, 1);
      CPU.vPT.setD(newOwnerOfPage, 1);
      CPU.vPT.setPageFrameNum(newOwnerOfPage, pageNum);
  }

  //this function, when the dirty bit was set, writes back into the files
  private static void writeToPGFile( int pageNum , int evictedOwnerOfPage) throws IOException {
      File outputPage = new File("../page_files/copy/" + (pageNum < 16 ? "0":"") + Integer.toHexString(pageNum) + ".pg");
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputPage));

      for( int i = 0; i < 256; i++ ) {
          String data = String.valueOf(CPU.PM.getPhysicalMem(pageNum, i));
          writer.write(data);
          writer.newLine();
      }
      writer.flush();
      writer.close();
  }

  static void resetR(TLBEntry[] TLB, VirtualPageTable pageTable) {
      for (TLBEntry aTLB : TLB) {
          aTLB.setR(0);
      }
      pageTable.resetRBit();
  }


  ///testsss
    private static int numOfPagesLeft = 0;
    static int hardMiss(int pageNum) throws IOException{
        if ( numOfPagesLeft < 16){  //there are still free pages available
            // just add them straight;
            writeToPhysicalMem(numOfPagesLeft, pageNum);
            numOfPagesLeft++;
            Driver.evicted = "N/A";
            return numOfPagesLeft - 1;
        }
        else{   //so, all pages have been used up, so we need to activate the clock replacement algorithm
            for(int i = (clockIndex + 1) % 256; i != clockIndex; i = (i + 1) % 256){
                if (CPU.vPT.getV(i) == 1  && CPU.vPT.getR(i)==0){
                    clockIndex = i;
                    Driver.evicted = Integer.toHexString(i);
                    Driver.dirty = 0;
                    return evict(pageNum, i);
                }
            }
        }
        System.out.println("Your CPU has exploded, please ask for better value products!!");
        return -1;
    }
    //in here i will go to the page=pageNum and write to it the file [address].pg

    //in here i will do the same thing as above, but i will also reset all values in the
    //old vPT entry
    private static int evict(int newOwnerOfPageIndex, int evictedPageOwnerIndex) throws IOException{

        //if vPT ==> D bit = 1, then write
        int pageNum = CPU.vPT.getPageFrameNum(evictedPageOwnerIndex);
        if (CPU.vPT.getD(evictedPageOwnerIndex) == 1) {
            Driver.dirty=1;
            writeToPGFile(pageNum, evictedPageOwnerIndex);
        }
        resetVRDBits(evictedPageOwnerIndex);
        writeToPhysicalMem(pageNum, newOwnerOfPageIndex);
        return pageNum;
    }

    //this goes and resets the TLB and virutal page table values back to invalid 0|0|0
    private static void resetVRDBits(int evictedAddress){
        for(int i=0; i< 8; i++){
            if (CPU.TLB[i].getvPageNum() == evictedAddress){
                CPU.TLB[i].setV(0);
                CPU.TLB[i].setR(0);
                CPU.TLB[i].setPageFrameNum(0);
                CPU.TLB[i].setD(0);
                break;
            }
        }
        CPU.vPT.setR(evictedAddress, 0);
        CPU.vPT.setV(evictedAddress, 0);
        CPU.vPT.setD(evictedAddress, 0);
        CPU.vPT.setPageFrameNum(evictedAddress, -1);
    }
}
