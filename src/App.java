import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {

    static Flow[] readFile(){
        Flow[] flows = null;
        try {
            Scanner myReader = new Scanner(new File("project5input.txt"));
            int NumberOfFlows =Integer.parseInt(myReader.nextLine());
            flows = new Flow[NumberOfFlows];
            int i = 0;
            while (myReader.hasNextLine()) {
              String[] data= myReader.nextLine().replaceAll("[ ]+", " ").split(" ");
              Flow flow = new Flow(data[0], Integer.parseInt(data[2]));
              flows[i] = flow;
              i++;
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          return flows;
    }
    
    public static void main(String[] args) throws Exception {
        Flow[] flows;
        flows = readFile();
    }
}
