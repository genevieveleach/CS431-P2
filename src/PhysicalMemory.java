public class PhysicalMemory {

  //TODO: figure out actual numbers
  private double ram[][] = new double[pageAmt][pageSize];

  PhysicalMemory() {
  }

  public double getPhysicalMemory(int row, int column) {
    return ram[row][column];
  }

  public void setPhysicalMemory(int row, int column, double value) {
    ram[row][column] = value;
  }
}
