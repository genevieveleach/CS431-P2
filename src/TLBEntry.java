public class TLBEntry extends PageTableEntry {

  private int vPageNum;

  TLBEntry(int pageFrameNum) {
    super(pageFrameNum);
  }

  public int getvPageNum() {
    return vPageNum;
  }

  public void setvPageNum(int vPageNum) {
    this.vPageNum = vPageNum;
  }

}
