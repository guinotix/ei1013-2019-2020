package practica2;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedCarreteras {
	private Map<String, Map<String, Integer>> red;

	/**
	 * Constructor
	 * 
	 * Crea una red de carreteras vacías.
	 */
	public RedCarreteras() {
		// TODO Ejercicio 2
		Map<String, Map<String, Integer>> redVacia = new HashMap<>();
		red = redVacia;
	}

	/**
	 * Valida que un tramo sea correcto.
	 * 
	 * Es decir, que ninguna de las dos ciudades sea null, y que la distancia sea
	 * mayor de cero. No se admiten tramos de una ciudad consigo misma. En el
	 * caso de que un tramo no sea válido produce una excepción.
	 * 
	 * @param una,
	 *            una ciudad
	 * @param otra,
	 *            la otra ciudad
	 * @param distancia,
	 *            la distancia del tramo
	 * @throws InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	private void validarTramo(String una, String otra, int distancia) {
		if (una == null || otra == null || una.equals(otra) || distancia < 1)
			throw new InvalidParameterException();
	}

	/**
	 * Devuelve un conjunto con todas las ciudades de la red.
	 */
	public Set<String> ciudades() {
		return red.keySet();
	}

	/**
	 * Añade un tramo a la red.
	 * 
	 * Valida que el tramo sea valido. Si alguna de las dos ciudades no existiá
	 * previamente en la red, la añade. El tramo se añadirá dos veces, una para
	 * cada ciudad. En caso de que el tramo ya existiera remplazará el valor de
	 * la distancia.
	 * 
	 * @return Distancia previa del tramo, -1 si el tramo no exitía.
	 * @throws InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	public int nuevoTramo(String una, String otra, int distancia) {
		//TODO Ejercicio 2
		validarTramo(una, otra, distancia);
		// Añadir las claves
		if (!red.containsKey(una)) {
			Map<String, Integer> mapaVecinos = new HashMap<>();
			red.put(una, mapaVecinos);
		}
		if (!red.containsKey(otra)) {
			Map<String, Integer> mapaVecinos = new HashMap<>();
			red.put(otra, mapaVecinos);
		}
		int distanciaPrevia = existeTramo(una, otra);
		red.get(una).put(otra, distancia);
		red.get(otra).put(una, distancia);
		return distanciaPrevia;
	}

	/**
	 * Comprueba si existe un tramo entre dos ciudades.
	 * 
	 * @return La distancia del tramo, o -1 si no existe
	 */
	public int existeTramo(String una, String otra) {
		if (!red.containsKey(una) || !red.containsKey(otra))
			return -1;

		Integer aux = red.get(una).get(otra);

		return (aux == null ? -1 : aux);
	}

	/**
	 * Borra el tramo entre dos ciudades si existe.
	 * 
	 * @return La distancia del tramo, o -1 si no existía.
	 */
	public int borraTramo(String una, String otra) {
		int d = existeTramo(una, otra);

		if (d < 0)
			return d;

		red.get(una).remove(otra);
		red.get(otra).remove(una);

		return d;
	}

	/**
	 * Comprueba si un camino es posible.
	 * 
	 * Un camino es una lista de ciudades. El camino es posible si existe un
	 * tramo entre cada par de ciudades consecutivas. Si dos ciudades
	 * consecutivas del camino son la misma el camino es posible y la distancia
	 * añadida es 0. Si el camino incluye una sóla ciudad de la red el resultado es 0.
	 * 
	 * @return La distancia total del camino, es decir la suma de las distancias
	 *         de los tramos, o -1 si el camino no es posible o no incluye ninguna ciudad.
	 */
	public int compruebaCamino(List<String> camino) {
		if (camino.isEmpty()) {
			return -1;
		} else if (camino.size() == 1) {
			return 0;
		} else {
			int distanciaTotal = 0;
			for (int i=0; i<camino.size()-1; i++) {
				if (camino.get(i) == null || camino.get(i+1) == null) {
					return -1;
				}
				int tramo = existeTramo(camino.get(i), camino.get(i+1));
				if (camino.get(i).equals(camino.get(i+1))) {
					distanciaTotal += 0;
				} else {
					if (tramo < 0) {
						return -1;
					} else {
						distanciaTotal += tramo;
					}
				}
			}
			return distanciaTotal;
		}
	}

	public String masProxima (String una) {
		Map<String, Integer> tramos = red.get(una);
		
		if (tramos == null)
			return null;
		
		String masCercana = null;
		int distancia = -1;
		
		Iterator<String> iter= tramos.keySet().iterator();
		while(iter.hasNext()) {
			String ciudad = iter.next();
			if (masCercana == null || (tramos.get(ciudad) < distancia)) {
				distancia = tramos.get(ciudad);
				masCercana = ciudad;
			}
		}
		
		return masCercana;
	}
	
}
