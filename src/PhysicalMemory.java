public class PhysicalMemory{
    /*
    physical memory has "12" bytes of addressable data points
    because the offset is 8 bits, that means that we have "4" bits as page values
    so...
    0x ABC      Page number==>[A][BC]<==Offset
    */
    private int ram[][] = new int[16][256];
    
    public PhysicalMemory() {
    }

    public int getPhysicalMem(int row, int col){
        return ram[row][col];
    }
    public void setPhysicalMem(int row, int col, int val){
        ram[row][col]= val;
    }
}

