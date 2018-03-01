public class CPU {
  Read File(){
    - open .txt
    int counter = 0;
    when counter/20 == 0
      OS.resetR()
    - process and pass to mmu
  }
  Class MMU{
    int R/W
    int address
    int data
    - getData(int, int, int){
      - Sets B
      - check TLB() > -1 if valid
      - check Pagetable() > -1 if valid
        h/m/s is set
      - if r/w == 1 set d = 1
        return page frame #
    }
  Read file cont......
    if (valid and Read)
      Physicalmem.Read(PF#, offset#)
    else if (Valid)
      pm.write(PF#, offset, data)
    else
      OS.pull(PT#)
  System.out.println(address + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
  }
}
