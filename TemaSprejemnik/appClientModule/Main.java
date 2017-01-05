
public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TemaSprejemnik1 ts1=new TemaSprejemnik1();
		TemaSprejemnik2 ts2=new TemaSprejemnik2();
		ts1.subscribe();
		ts2.subscribe();
		
		try {
			// Zaspimo, da lahko kaj dobimo
			Thread.sleep(150000); // 150s
			} catch (InterruptedException e) {
			System.out.println("Napaka - thread: ");
			e.printStackTrace();
			}  
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}