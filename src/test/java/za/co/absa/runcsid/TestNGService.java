package za.co.absa.runcsid;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


//@RestController
public class TestNGService {
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY_MS = 2000;


//    public static void main(String[] args) {
//
//        Timer timer = new Timer();

//        timer.schedule(new IdleTask(), 0, 60 * 200);
//
//    }
//    private  static class IdleTask extends TimerTask{
//        private long lastActivityTime = System.currentTimeMillis();
//
//        @Override
//        public void run(){
//            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
//            if (pointerInfo != null){
//                long idleTime = System.currentTimeMillis() - lastActivityTime;
//                System.out.println(idleTime/1000 +"Seconds");
//
//                if (idleTime >= DELAY){
//                    System.out.println(idleTime/1000+" Seconds");
//                    TestNG testNG = new TestNG();
//                    TestListenerAdapter tla = new TestListenerAdapter();
//                    testNG.addListener(tla);
//                    List<String> suite = new ArrayList<String>();
//                    suite.add("S:\\AB016PN\\btp\\btpAutomation\\testng.xml");
//                    testNG.setTestSuites(suite);
//                    testNG.run();
//                }
//            }
//            lastActivityTime = System.currentTimeMillis();
//            //System.out.println(lastActivityTime +" Seconds");
//        }
//    }


    public static void main (String[] args) throws InterruptedException {
        int attemptCount = 0;

        long interval = 5000;
        int i = 0;
        while (true) {
            i++;
            if (i == 5) {

                memoryUsageBefore();
                memoryUsageAfter();
                i = 0;
                System.gc();
                System.out.println("\033[H\033[2J");
                System.out.flush();
                continue;
            }
            try {
                Response response = RestAssured.get(System.getenv("baseURL"));
                if (response.body().asString().length() > 2 && response.getStatusCode() == 200) {
                    System.out.println("Endpoint is up, executing...");
                    // System.out.println(response.body().asString().length());
                    TestNG testNG = new TestNG();
                    TestListenerAdapter tla = new TestListenerAdapter();
                    testNG.addListener(tla);
                    List<String> suite = new ArrayList<String>();
                    suite.add("S:\\AB016PN\\btp\\btpAutomation\\testng.xml");
                    testNG.setTestSuites(suite);
                    testNG.run();
                    i = 0;
                    continue; //reset counter to 1 after running successfully...

                } else {
                    System.out.println("No data loaded in the body as yet: " + i);


                }
            } catch (Exception e) {
                System.out.println("endpoint down");
                Thread.sleep(1000);
            }
            attemptCount++;
            if (attemptCount == MAX_RETRIES){
                System.out.println("Reached max, starting..");
                attemptCount=0;
            }
        }



    }

    protected static void memoryUsageBefore(){
        Runtime runtime = Runtime.getRuntime();
        long freeMemoryBefore = runtime.freeMemory();
        System.out.println("Free Memory Before loop: "+ freeMemoryBefore);
    }

    protected static void memoryUsageAfter(){
        Runtime runtime = Runtime.getRuntime();
        long freeMemoryAfter = runtime.freeMemory();
        //long memoryUsange= runtime.freeMemory();
        System.out.println("Free Memory After loop: "+ freeMemoryAfter);
    }

}
