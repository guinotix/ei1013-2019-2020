package practica7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;

//import ejer1.EDListGraph.Node;

public class MiniFacebook {

	 
	public static EDGraph<String, Object> leerGrafo (String nomfich) {
		//TODO
		Scanner fichero = null;
		try {
			fichero = new Scanner(new FileInputStream(nomfich));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		EDGraph<String, Object> facebook = new EDListGraph<>();

		while (fichero.hasNextLine()) {
			String contactoA = fichero.next();
			String contactoB = fichero.next();

			if (facebook.getNodeIndex(contactoA) < 0) {
				facebook.insertNode(contactoA);
			}
			if (facebook.getNodeIndex(contactoB) < 0) {
				facebook.insertNode(contactoB);
			}
			if (facebook.getNodeIndex(contactoA) >= 0 && facebook.getNodeIndex(contactoB) >= 0) {
				EDEdge<Object> relacion = new EDEdge<>(facebook.getNodeIndex(contactoA), facebook.getNodeIndex(contactoB));
				facebook.insertEdge(relacion);
			}
		}
		return facebook;
	}
	
		
	public static void main(String[] args) throws IOException {

		String filename = "miniFacebook.txt";

		EDGraph<String, Object> grafo = leerGrafo(filename);

		if (grafo == null)
			System.out.println("No se pudo leer el fichero " + filename);
							
		grafo.printGraphStructure();
	}
}
