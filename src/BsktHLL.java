public class BsktHLL {

    int numberOfHLL;
    int numberOfBitsPerHLLRegister;
    int k;
    int numberOfRegisterPerHLL;

    HyperLogLog[] bskt;

    HashFunctions hashFunctions;
    
    public BsktHLL(int numberOfHLL,int numberOfRegisterPerHLL, int numberOfBitsPerHLLRegister, int k){
        this.numberOfHLL = numberOfHLL;
        this.numberOfBitsPerHLLRegister = numberOfBitsPerHLLRegister;
        this.k = k;
        this.hashFunctions = new HashFunctions(k, numberOfHLL);
        this.numberOfRegisterPerHLL= numberOfRegisterPerHLL;
        bskt = new HyperLogLog[numberOfHLL];
        for(int i=0;i<numberOfHLL;i++){
            bskt[i] = new HyperLogLog(numberOfRegisterPerHLL, numberOfBitsPerHLLRegister);
        }
    }

    public void RecordFlows(Flow[] flows) {
        for (Flow flow : flows) {
            RecordFlow(flow);
        }
    }

    private void RecordFlow(Flow flow) {
        RecordPackets(flow.flowId, flow.packets);
    }

    public void RecordPackets(String flowId, int[] packets) {
        for(int i=0;i<k;i++){
            bskt[hashFunctions.Hash(flowId, i)].RecordPackets(packets);
        }
    }

}
