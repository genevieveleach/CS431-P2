import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardCopyOption;

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
  static int clockIndex;
  
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

  static void writePage(int pageNum) throws IOException {
      String temp;
      if(pageNum < 16) {
        temp = "0" + Integer.toHexString(pageNum).toUpperCase();
      }
      else {
        temp = Integer.toHexString(pageNum).toUpperCase();
      }

      File outputPage = new File("../page_files/copy/" + temp + ".pg");
      BufferedWriter writer = Files.newBufferedWriter(outputPage.toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE);
      for( int i = 0; i < 256; i++) {
        double data = CPU.PM.getPhysicalMem( clockIndex, i );
        writer.write(temp);
        writer.newLine();
      }
  }
  
  static void writeMemory( int pageNum ) throws IOException {
      String temp;
      if(pageNum < 16) {
        temp = "0" + Integer.toHexString(pageNum).toUpperCase();
      }
      else {
        temp = Integer.toHexString(pageNum).toUpperCase();
      }
        
      File inputPage = new File("../page_files/copy/" + temp + ".pg");
      BufferedReader reader = new BufferedReader( new FileReader(inputPage) );
      CPU.vPT.setV(pageNum, 1);
      CPU.vPT.setR(pageNum, 1);
      CPU.vPT.setD(pageNum, 0);
      CPU.vPT.setPageFrameNum(pageNum, clockIndex);
      for( int i = 0; i < 256; i++ ) {
        CPU.PM.setPhysicalMem( clockIndex, i, Integer.parseInt(reader.readLine()));
      }
  }
  
  static int hardMiss( int pageNum ) throws IOException {
      boolean check = true;
      while(check) {
        if(CPU.vPT.getPageFrameNum(clockIndex) == -1) {
            writeMemory( pageNum );
            check = false;
        }
        else if(CPU.vPT.getR(clockIndex) == 0) {
            if(CPU.vPT.getD(clockIndex) == 1)
                writePage( pageNum );
            writeMemory( pageNum );
            check = false;
        }
        else {
            CPU.vPT.setR(clockIndex, 0);
        }
        clockIndex++;
        if(clockIndex > 15)
            clockIndex = 0;
      }
      return clockIndex - 1;
  }
  

  static void resetR(TLBEntry[] TLB, VirtualPageTable pageTable) {
      for(int i = 0; i < TLB.length; i++) {
          TLB[i].setR(0);
      }
      pageTable.resetRBit();
  }
}
