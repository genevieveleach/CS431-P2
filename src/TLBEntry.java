public class TLBEntry extends PageTableEntry {

    private int vPageNum;

    public TLBEntry(int pageFrameNum, int vPageNum) {
        super(pageFrameNum);
        this.vPageNum = vPageNum;
    }
    public TLBEntry() {
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
