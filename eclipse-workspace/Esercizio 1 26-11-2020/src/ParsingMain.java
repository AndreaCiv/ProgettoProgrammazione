import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;
import java.io.FileNotFoundException;

public class ParsingMain {

	public static void main(String[] args) {
		Vector<String> righe = new Vector<String>();
		try {
			Scanner input = new Scanner(new BufferedReader(new FileReader("prova.txt")));
			do{
				righe.add(input.nextLine());
			}while(input.hasNextLine());
			input.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERRORE: File non trovato");
			System.out.println(e.toString());
			return;
		}
		Vector<String[]> aziende = new Vector<String[]>();
		int i = 0;
		
		do {
			String[] rigaSplittata = new String[4];
			dividiStringa(righe.elementAt(i),rigaSplittata);
			aziende.add(rigaSplittata);
			i++;
		}while(i<righe.size());
		
		for(int j=0; j<aziende.size(); j++)
		{
			for(int x=0; x<4; x++)
			{
				String string = aziende.elementAt(j)[x];
				System.out.print(string + " ");
			}
			System.out.println();
		}
	}
	
	public static void dividiStringa (String riga, String[] destinazione)
	{
		destinazione = riga.split(",");
	}
	
}
