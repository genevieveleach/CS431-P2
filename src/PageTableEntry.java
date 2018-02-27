public class PageTableEntry {

  private int pageFrameNum;
  private int vBit;
  private int rBit;
  private int dBit;

  PageTableEntry(int pageFrameNum) {
    this.pageFrameNum = pageFrameNum;
    this.vBit = 0;
    this.rBit = 0;
    this.dBit = 0;
  }

  public int getPageFrameNum() {
    return pageFrameNum;
  }

  public void setPageFrameNum(int pageFrameNum) {
    this.pageFrameNum = pageFrameNum;
  }

  public int getvBit() {
    return vBit;
  }

  public void setvBit(int vBit) {
    this.vBit = vBit;
  }

  public int getrBit() {
    return rBit;
  }

  public void setrBit(int r) {
    this.rBit = r;
  }

  public int getdBit() {
    return dBit;
  }

  public void setdBit(int dBit) {
    this.dBit = dBit;
  }
}
