public class TLBEntry extends PageTableEntry {

    private int vPageNum;

    TLBEntry(int pageFrameNum, int vPageNum) {
        super(pageFrameNum);
        this.vPageNum = vPageNum;
    }
    TLBEntry() {
        super(-1);
        vPageNum = -1;
    }

    public int getvPageNum() {
        return vPageNum;
    }
    public void setvPageNum(int vPageNum) {
        this.vPageNum = vPageNum;
    }
}
