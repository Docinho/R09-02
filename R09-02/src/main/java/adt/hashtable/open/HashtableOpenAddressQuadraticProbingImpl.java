package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      if (super.isFull()) {
         throw new HashtableOverflowException();
      }

         int i = 0;
         int j = this.getHashFunction().hash(element, i);
         while (table[j] != null && !(deletedElement.equals(table[j])) && !(element.equals(table[j]))) {
            j = this.getHashFunction().hash(element, ++i);
            super.COLLISIONS++;
         }
         super.table[j] = element;
         super.elements++;
      
   }

   @Override
   public void remove(T element) {
      if (!super.isEmpty()) {
         int indice = this.indexOf(element);
         if (indice != -1) {
            super.table[indice] = deletedElement;
            super.elements--;
         }
      }
   }

   @Override
   public T search(T element) {
		T objeto = null;
		int indice = indexOf(element);
		if (!super.isEmpty() && indice != -1) {
			objeto = (T) table[indice];

		}
		return objeto;
   }

   @Override
   public int indexOf(T element) {
      int indice = -1;
      if (!super.isEmpty()) {
         int i = 0;
         int j = this.getHashFunction().hash(element, i);
         while (i < super.table.length && indice == -1 && super.table[j] != null) {
            if (super.table[j].equals(element)) 
               indice = j;
            
            j = this.getHashFunction().hash(element, ++i);
         }

      }
      return indice;
   }

   public HashFunctionQuadraticProbing<T> getHashFunction() {
      return (HashFunctionQuadraticProbing<T>) this.hashFunction;
   }

}
