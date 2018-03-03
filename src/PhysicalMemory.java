public class PhysicalMemory {

  double ram[][] = new double[256][256];

  PhysicalMemory() {
    //instantiation?
  }

  public double getPhysicalMemory(int row, int column) {
    return ram[row][column];
  }

  public void setPhysicalMemory(int row, int column, double value) {
    ram[row][column] = value;
  }
}
