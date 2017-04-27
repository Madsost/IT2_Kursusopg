import java.util.ArrayList;

public class MainTest {
	public static void main(String[] args){
		boolean testB = true;
		Main test = new Main(testB);
		
		// Lav ækvivalens-klasser
		ArrayList<String> tempTEST = new ArrayList<>();
		ArrayList<String> pulsTEST = new ArrayList<>();
		tempTEST.add("");
		pulsTEST.add("");
		
		// Set dataset til testkørsel
		test.setData(tempTEST, pulsTEST);
		
		try{Thread.sleep(2000);}catch(InterruptedException e){}
		
		// Kør test på validate
		test.validate();
	}
}
