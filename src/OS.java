import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OS {
  /*pointer to array values in page table
  ReplacePageEntry()
  int pointer
  ReadPage()
    - reads pagefile and loads it into memory
      -.pg
  WritePage()
    - write to .pg file from Physical memory
    - reset d bit
  ResetR()*/

  //TODO: as of rn is actually just get page but we'll fix that
  int clockIndex;
  
  static File initPages(String pageNum) throws IOException {
      clockIndex = 0;
      String src = "../page_files/original/" + pageNum + ".pg";
      String dest = "../page_files/copy/" + pageNum + ".pg";
      Files.copy(Paths.get(src), Paths.get(dest));
      return new File(dest);
  }

  static void writePage(String pageFrameNum) throws IOException {
      int pageN = Integer.parseInt( pageNum, 16 );
      File outputPage = new File("../page_files/copy/" + pageNum + ".pg");
      BufferedWriter writer = Files.newBufferedWriter(outputPage, "US_ASCII");
      for( int i = 0; i < 256; i++) {
        int temp = (int) physicalMemory.getPhysicalMem( pageN, i );
        writer.write(temp);
        writer.newLine();
      }
  }
  
  static int writeMemory( int pageNum ) throws IOException {
      String temp = Integer.toHexString( pageNum );
      File inputPage = new File("../page_files/copy/" + temp + ".pg");
      BufferedReader reader = new BufferedReader( new FileReader(inputPage) );
      vPT.setV(pageNum, 1);
      vPT.setR(pageNum, 1);
      vPT.setD(pageNum, 0);
      vPT.setPageFrameNum(pageNum, clockIndex);
      for( int i = 0; i < 256; i++ ) {
        physicalMemory.setPhysicalMem( clockIndex, i, Integer.parseInt(reader.readLine()));
      }
  }
  
  static int hardMiss( int pageNum ) throws IOException {
      boolean check = true;
      while(check) {
        if(vPT.getPageFrameNum(clockIndex) == -1) {
            writeMemory( pageNum );
            check = false;
        }
        else if(vPT.getR(clockIndex) == 0) {
            if(vPT.getD(clockIndex) == 1)
                writePage( pageNum );
            writeMemory( pageNum );
            check = false;
        }
        else {
            vPT.setR(clockIndex, 0);
        }
        clockIndex++;
        if(clockIndex > 15)
            clockIndex = 0;
        return clockIndex - 1;
  }

  static void resetR(TLBEntry[] TLB, VirtualPageTable pageTable) {
      for(int i = 0; i < TLB.length; i++) {
          TLB[i].setR(0);
      }
      pageTable.resetRBit();
  }
}
