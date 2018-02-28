public class TLBEntry extends PageTableEntry {

  int vPageNum;

  TLBEntry(int pageFrameNum, int vPageNum) {
    super(pageFrameNum);
    this.vPageNum = vPageNum;
  }

  TLBEntry() {
    super();
    vPageNum = -1;
  }

  public int getvPageNum() {
    return vPageNum;
  }

  public void setvPageNum(int vPageNum) {
    this.vPageNum = vPageNum;
  }

}
