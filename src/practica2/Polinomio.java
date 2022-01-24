package practica2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Polinomio {

	// La lista de monomios
	private List<Monomio> datos = new LinkedList<Monomio>();

	/**
	 * Constructor por defecto. La lista de monomios está vacía
	 */
	public Polinomio() {
	}

	/**
	 * Constructor a partir de un vector. Toma los coeficientes de los monomios
	 * de los valores almacenados en el vector, y los exponentes son las
	 * posiciones dentro del vector. Si <code>v[i]</code> contiene
	 * <code>a</code> el monomio contruido será aX^i. <br>
	 * 
	 * Por ejemplo: <br>
	 * 
	 * v = [-1, 0, 2] -> 2X^2 -1X^0
	 * 
	 * @param v
	 */
	public Polinomio(double v[]) {
		for (int i=0; i<v.length; i++) {
			if (Cero.esCero(v[i]) == false) {
				datos.add(new Monomio(v[i], i));
			}
		}
	}

	/**
	 * Constructor copia
	 * 
	 * @param otro
	 * @throws <code>NullPointerException</code>
	 *             si el parámetro es nulo
	 */
	public Polinomio(Polinomio otro) {
		if (otro == null)
			throw new NullPointerException();

		for (Monomio item : otro.datos)
			datos.add(new Monomio(item));
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		

		boolean primero = true;

		for (int i = 0; i <datos.size(); i++) {
			Monomio item = datos.get(i);

			if (item.coeficiente < 0) {
				str.append('-');
				if (!primero)
					str.append(' ');
			} else if (!primero)
				str.append("+ ");

			str.append(Math.abs(item.coeficiente));
			if (item.exponente > 0)
				str.append('X');
			if (item.exponente > 1)
				str.append("^" + item.exponente);

			if (i < datos.size()-1)
				str.append(' ');

			primero = false;
		}
		if (primero)
			str.append("0.0");

		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Polinomio other = (Polinomio) obj;

		if (this.datos.size() != other.datos.size())
			return false;

		Iterator<Monomio> iter1 = this.datos.iterator();
		Iterator<Monomio> iter2 = other.datos.iterator();

		while (iter1.hasNext())
			if (!(iter1.next().equals(iter2.next())))
				return false;

		return true;
	}

	/**
	 * Devuelve la lista de monomios
	 *
	 */
	public List<Monomio> monomios() {
		return datos;
	}

	/**
	 * Suma un polinomio sobre <code>this</code>, es decir, modificando el
	 * polinomio local. Debe permitir la auto autosuma, es decir,
	 * <code>polinomio.sumar(polinomio)</code> debe dar un resultado correcto.
	 *
	 * @param otro
	 * @return <code>this<\code>
	 * @throws <code>NullPointeExcepction</code> en caso de que el parámetro sea <code>null</code>.
	 */
	public void sumar(Polinomio otro) {
		//TODO Ejercicio 1
		if (otro == null) {
			throw new NullPointerException();
		} else {
			int posDatos = 0;
			int posPoli = 0;
			while (posDatos < datos.size() && posPoli < otro.datos.size()) {
				Monomio monomioA = datos.get(posDatos);
				Monomio monomioB = otro.datos.get(posPoli);

				if (monomioA.exponente == monomioB.exponente) {
					monomioA.coeficiente += monomioB.coeficiente;
					if (Cero.esCero(monomioA.coeficiente) == true) {
						datos.remove(posDatos);
					} else {
						posDatos++;
					}
					posPoli++;
				} else if (monomioA.exponente < monomioB.exponente) {
					posDatos++;
				} else {
					datos.add(posDatos, monomioB);
					posPoli++;
				}
			}
			while (posPoli < otro.datos.size()) {
				datos.add(otro.datos.get(posPoli++));
			}
		}
	}
	

	/** Multiplica el polinomio <code>this</code> por un monomio. 
	 * @param mono
	 */
	public void multiplicarMonomio(Monomio mono) {
		// TODO Ejercicio 1
		if (Cero.esCero(mono.coeficiente) == true) {
			datos.clear();
		} else {
			int i=0;
			for (Monomio monomio : datos) {
				monomio.coeficiente *= mono.coeficiente;
				monomio.exponente += mono.exponente;
				datos.set(i++, monomio);
			}
		}
	}
}
