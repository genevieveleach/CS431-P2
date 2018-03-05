public class PageTableEntry{

    private int pageFrameNum;
    private int v, r, d;

    public PageTableEntry(int frameNum){
        this.pageFrameNum=frameNum;
        v = 0; r = 0; d = 0;
    }

    //getters and setters
    public void setV(int validBit){ v = validBit; }
    public void setR(int readBit) { r = readBit; }
    public void setD(int dirtyBit) { d = dirtyBit; }
    public void setPageFrameNum(int pFrameNum) { this.pageFrameNum = pFrameNum; }

    public int getV(){ return v; }
    public int getD(){ return d; }
    public int getR(){ return r; }
    public int getPageFrameNum(){ return pageFrameNum; }
}
