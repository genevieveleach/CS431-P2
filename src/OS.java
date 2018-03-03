import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.BufferedWriter;

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
  private File readPage(String pageNum) throws IOException {
      String src = "../page_files/original/" + pageNum + ".pg";
      String dest = "../page_files/copy/" + pageNum + ".pg";
      Files.copy(Paths.get(src), Paths.get(dest));
      return new File(dest);
  }

  private void writePage( String pageNum ) throws IOException {
      int pageN = Integer.parseInt( pageNum, 16 );
      File outputPage = new File("../page_files/copy/" + pageNum + ".pg");
      BufferedWriter writer = Files.newBufferedWriter(outputPage, "US-ASCII");
      for( int i = 0; i < 256; i++) {
        int temp = (int) physicalMemory.getPhysicalMem( pageN, i );
        writer.write(temp);
        writer.newLine();
      }
  }

  private void resetR(TLBEntry[] TLB, VirtualPageTable pageTable) {
      for(int i = 0; i < TLB.length; i++) {
          TLB[i].setR(0);
      }
      pageTable.resetRBit();
  }
}
