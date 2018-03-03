public class VirtualPageTable {
  // this is the front half of the CPU addressable bits
  //      page table number==>[9A][BC]
  // this is that list
  private PageTableEntry[] virtPageTable = new PageTableEntry[256];

  public void resetRBit(){
    for (int i=0; i< 256; i++)
      virtPageTable[i].setR(0);
  }


  //getters and setters
  public int getV(int index) { return virtPageTable[index].getV(); }
  public int getR(int index) { return virtPageTable[index].getR(); }
  public int getD(int index) { return virtPageTable[index].getD(); }
  public int getPageFrameNum(int index) { return virtPageTable[index].getPageFrameNum(); }


  public void getV(int index, int bit) { virtPageTable[index].setV(bit); }
  public void getR(int index, int bit) { virtPageTable[index].setR(bit); }
  public void getD(int index, int bit) { virtPageTable[index].setD(bit); }
  public void getPageFrameNum(int index, int bit) { virtPageTable[index].setPageFrameNum(bit); }
}
