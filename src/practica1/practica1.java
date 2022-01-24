package practica1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class practica1 {

    /**
     *  Método que toma dos conjuntos de enteros y separa los elementos entre aquellos que sólo aparecen una vez
     *  y aquellos que aparecen repetidos. El método modifica los conjuntos que toma como parámetros.
     * @param unicos    A la entrada un conjunto de enteros. A la sálida los elementos que solo aparecen en uno de
     *                  los conjuntos.
     * @param repetidos A la entrada un conjunto de enteros. A la salida los elementos que aparecen en ambos conjuntos.
     */
    static public void separa(Set<String> unicos, Set<String> repetidos) {
        Iterator<String> iter = repetidos.iterator();
        while (iter.hasNext()) {
            String elemento = iter.next();
            if (unicos.contains(elemento)) {
                unicos.remove(elemento);
            } else {
                unicos.add(elemento);
                iter.remove();
            }
        }
    }

    /**
     *  Toma un iterador a una colección de enteros positivos y devuelve como resultado un conjunto con aquellos elementos
     *  de la colección que no son múltiplos de algún otro de la colección. Los ceros son descartados
     * @param iter  Iterador a una colección de enteros
     * @return Conjunto de de enteros.
     */
    static public Set<Integer> filtra(Iterator<Integer> iter) {
        Set<Integer> auxiliar = new HashSet<>();
        while (iter.hasNext()) {
            Integer elemento = iter.next();
            if (elemento == 1) {
                Set<Integer> unico = new HashSet<>();
                unico.add(elemento);
                return unico;
            }
            if (elemento > 0) {
                auxiliar.add(elemento);
            }
        }
        if (auxiliar.isEmpty()) {
            return auxiliar;
        } else {
            Set<Integer> conjuntoA = new HashSet<>(auxiliar);
            Set<Integer> divisores = new HashSet<>();
            for (Integer dividendo : auxiliar) {
                for (Integer divisor : conjuntoA) {
                    if (dividendo != divisor && dividendo % divisor == 0) {
                        divisores.add(dividendo);
                    }
                }
            }
            auxiliar.removeAll(divisores);
            return auxiliar;
        }
    }

    /**
     * Toma una colección de conjuntos de <i>String</i> y devuelve como resultado un conjunto con aquellos <i>String </i>
     * Que aparecen en al menos dos conjuntos de la colección.
     * @param col Coleccion de conjuntos de <i>String</i>
     * @return Conjunto de <i>String</i> repetidos. 
     */
    static public Set<String> repetidos(Collection<Set<String>> col) {
        Set<String> aux = new HashSet<>();
        Set<String> res = new HashSet<>();
        Iterator<Set<String>> iterCol = col.iterator();
        while (iterCol.hasNext()) {
            Set<String> subconjunto = iterCol.next();
            Iterator<String> iterConj = subconjunto.iterator();
            while (iterConj.hasNext()) {
                String elemento = iterConj.next();
                if (!aux.contains(elemento)) {
                    aux.add(elemento);
                } else {
                    res.add(elemento);
                }
            }
        }
        return res;
    }
}
