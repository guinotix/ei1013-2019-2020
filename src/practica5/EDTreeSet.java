package practica5;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.Iterator;


public class EDTreeSet<E extends Comparable <E>> implements Set<E>{

    private Object EDTreeSet;

    protected class BinaryNode {
        protected E data;
        protected BinaryNode left;
        protected BinaryNode right;
        
        BinaryNode(E data){
            this.data = data;
        }
        BinaryNode(E data, BinaryNode lnode, BinaryNode rnode) {
            this.data = data;
            this.left = lnode;
            this.right = rnode;
        }
    }
    
    private BinaryNode root;
    private Comparator<? super E> comparator;
    private int size; //number of elements
    protected boolean insertReturn; //Return value for the public insert method
    protected E removeReturn; //Return value for the public remove method

    

    public EDTreeSet() {
        root = null;
        comparator = null;
        size = 0;
    }
    
    public EDTreeSet(Comparator<? super E> comp) {
        root = null;
        comparator = comp;
        size = 0;
    }
    
    public EDTreeSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }
    
    public EDTreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }
    
    private int compare(E left, E right) {
        if (comparator != null) { //A comparator is defined
            return comparator.compare(left,right);
        }
        else {
            return (((Comparable<E>) left).compareTo(right));
        }
    }

    public E first() {
        //TODO
        if (size == 0) {
            return null;
        }
        BinaryNode aux = root;
        while (aux != null) {
            if (aux.left == null) {
                break;
            } else {
                aux = aux.left;
            }
        }
        return aux.data;
    }
    
    public E last() {
        //TODO
        if (size == 0) {
            return null;
        }
        BinaryNode aux = root;
        while (aux != null) {
            if (aux.right == null) {
                break;
            } else {
                aux = aux.right;
            }
        }
        return aux.data;
    }

    @Override
    public boolean add(E item) {
        //TODO
        root = add(root, item);
        return insertReturn;
    }
    private BinaryNode add(BinaryNode node, E item) {
        if (node == null) {
            size++;
            insertReturn = true;
            return new BinaryNode(item);
        }
        if (compare(node.data, item) < 0) {
            node.right = add(node.right, item);
        } else if (compare(node.data, item) > 0) {
            node.left = add(node.left, item);
        } else {
            insertReturn = false;
        }
        return node;
    }

    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        if (arg0==null) throw new NullPointerException();
        boolean changed = false;
        for (E e: arg0) {
            boolean res=add(e);
            if (!changed && res) changed = true;
        }
        return (changed);
    }

    @Override
    public void clear() {
        root = null; 
        size = 0;
    }

    @Override
    public boolean contains(Object arg0) {
        //TODO
        BinaryNode aux = root;
        while (aux != null) {
            if (compare(aux.data, (E) arg0) > 0) {
                aux = aux.left;
            } else if (compare(aux.data, (E) arg0) < 0) {
                aux = aux.right;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        if (arg0 == null) throw new NullPointerException();
        boolean cont=true;
        Iterator<?> it=arg0.iterator();
        while (it.hasNext() && cont) {
            cont = contains(it.next());
        }
        return cont;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public boolean remove(Object arg0) {
        //TODO
        if (isEmpty()) {
            removeReturn = null;
            return false;
        }
        root = searchRemove(root, (E) arg0);
        if (removeReturn != null) {
            size--;
        }
        return removeReturn != null;
    }
    private BinaryNode searchRemove(BinaryNode node, E item) {
        if (node == null) {
            removeReturn = null;
            return null;
        }
        // Recorrido del arbol binario
        else if (compare(node.data, item) < 0) {
            node.right = searchRemove(node.right, item);
        }
        else if (compare(node.data, item) > 0) {
            node.left = searchRemove(node.left, item);
        }
        // Si la clave es igual, es el nodo a borrar
        else {
            return removeNode(node);
        }
        return node;
    }
    private BinaryNode removeNode(BinaryNode node) {
        removeReturn = node.data;
        // Nodo tiene dos hijos
        if (node.left != null && node.right != null) {
            BinaryNode parent = maximumLefty(node.left);
            node.data = parent.data;
            node.left = searchRemove(node.left, parent.data);
            return node;
        }
        // Nodo tiene hijo derecho
        else if (node.left == null && node.right != null) {
            return node.right;
        }
        // Nodo tiene hijo izquierdo
        else if (node.right == null && node.left != null) {
            return node.left;
        }
        // No tiene hijos
        else return null;
    }
    private BinaryNode maximumLefty(BinaryNode node) {
        if (node.right == null) {
            return node;
        }
        return maximumLefty(node.right);
    }

    public E ceiling(E e) {
        //TODO
        BinaryNode node = ceiling(root, e);
        if (node != null) {
            return node.data;
        }
        return null;
    }
    private BinaryNode ceiling(BinaryNode node, E item) {
        if (node == null) {
            return null;
        }
        if (compare(node.data, item) == 0) {
            return node;
        }
        if (compare(node.data, item) < 0) {
            return ceiling(node.right, item);
        }
        BinaryNode leftElem = ceiling(node.left, item);
        if (leftElem != null) {
            if (compare(leftElem.data, item) >= 0) {
                return leftElem;
            }
            return node;
        } else {
            return node;
        }
    }
    
    @Override
    public boolean removeAll(Collection<?> arg0) {
        if (arg0==null) throw new NullPointerException();
        int originalSize=size();
        int newSize = originalSize;
        Object [] v = this.toArray();
        for (int i=0; i<v.length; i++) {
            if (arg0.contains(v[i])) {
                remove(v[i]);
                newSize--;
            }
        }
        return (originalSize!=newSize);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        if (arg0==null) throw new NullPointerException();
        int originalS = size();
        int newS = originalS;
        Object[] v = this.toArray();
        for (int i=0; i<v.length; i++) {
            if (!arg0.contains(v[i])) {
                remove(v[i]);
                newS--;
            }
        }
        return (originalS!=newS);
        
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        
        Object[] v = new Object[size()];
        toArray(0,root,v);
        
        return v;
    }

    private int toArray (int pos, BinaryNode r, Object[] v) {
        if (r!=null) {
            if (r.left!=null) pos = toArray(pos,r.left, v);
            //System.out.println("toArray pos-> "+pos +" data--> "+r.data);
            v[pos] = r.data;
            pos++;
            if (r.right!=null) pos =toArray(pos,r.right,v);
        }
        return pos;
    }
    
    @Override
    public <T> T[] toArray(T[] arg0) {
        if (arg0 == null) throw new NullPointerException();
        int n=size();
        if (n > arg0.length) 
            arg0=(T[]) new Object[n];
        toArray(0,root,arg0);
        
        return arg0;
    }

    
    /**Returns an String with the data in the nodes
     * in inorder
     */
     public String toString() {
         return toString(root) + " - size:" + size;
     }
     
     private String toString(BinaryNode r) {
         String s="";
         if (r != null) {
             String sl=toString(r.left);
             String sd=toString(r.right);
             if (sl.length() >0) 
                 s = sl + ", ";
             s = s + r.data;
             if (sd.length() > 0)
                 s = s + ", " + sd;
         }
         return s;
     }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

}
