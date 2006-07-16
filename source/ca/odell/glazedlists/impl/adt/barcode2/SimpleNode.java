/* Glazed Lists                                                 (c) 2003-2005 */
/* http://publicobject.com/glazedlists/                      publicobject.com,*/
/*                                                     O'Dell Engineering Ltd.*/
package ca.odell.glazedlists.impl.adt.barcode2;

import java.util.List;
import java.util.Arrays;

/*
 # some M4 Macros that make it easy to use m4 with Java










  M4 Macros














# define a function NODE_WIDTH(boolean) to get the node's size for this color

    


# define a function NODE_SIZE(node, colors) to no node.nodeSize()

   


# define a function to refresh counts

   



*/
/*[ BEGIN_M4_JAVA ]*/   

/**
 * A node in a tree which supports both a value and compressed nodes that
 * contain a size, useful for index offsetting.
 *
 * <p>Note that the <code>counts</code> summary member is created lazily when
 * this node is given children. This causes the code to be less easy to read,
 * but it means we can put off about a huge number of object allocations since
 * 50% of the nodes in an arbitrary tree are leaf nodes, and these leaf nodes
 * now don't have counts.
 *
 * @author <a href="mailto:jesse@swank.ca">Jesse Wilson</a>
 */
class SimpleNode<V> implements Element<V> {

    /** the number of elements of each color in this subtree */
     
    int count1;
    
    
     

      


    /** the node's value */
    V value;

      

    /** values for managing the node within the tree */
    byte height;
    SimpleNode<V> left, right, parent;

    /** whether this node is consistent in the sorting order */
    boolean sorted = true;

    /**
     * Create a new node.
     *
     * @param color a bitmask value such as 1, 2, 4, 8 or 16.
     * @param size the size of the node
     * @param value the value of the node
     * @param parent the parent node in the tree, or <code>null</code> for the
     *      root node.
     */
    public SimpleNode/**/(   int size, V value, SimpleNode/**/<V> parent) {
          
         assert(size == 1); 
        this.value = value;
        this.height = 1;
        this.parent = parent;

         
        count1 += size;
        
        
         
    }

    /**
     * Get the value of this element.
     */
    public V get() {
        return value;
    }

    /**
     * Set the value of this element.
     */
    public void set(V value) {
        this.value = value;
    }

    /**
     * Get the color of this element.
     */
    public byte getColor() {
        return  1 ;
    }

    /**
     * The size of this entire node, including the left child, this node
     * and the right child.
     */
    final int size(byte colors) {
        // total the values of the specified array for the specified colors.
        int result = 0;

         
        if((colors & 1) != 0) result += count1;
        
        
         
        return result;
    }

      

    /**
     * Update the counts member variable by examining the counts of
     * the child nodes and the size member variable.
     */
    final void refreshCounts( boolean countSelf ) {

         
        count1 = 0;
        
        
         

        // left child
        if(left != null) {
             
            count1 += left.count1;
            
            
             
        }

        // right child
        if(right != null) {
             
            count1 += right.count1;
            
            
             
        }

        // this node
         
        count1 += countSelf ? 1 : 0;
        
        
         
    }

    /** {@inheritDoc} */
    public String toString() {
        return toString(Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F", "G", "H"}));
    }

    /**
     * Write this node out as a String, using the specified colors to write
     * each of the node values.
     */
    String toString(List colors) {
        StringBuffer result = new StringBuffer();
        asTree(0, result, colors);
        return result.toString();
    }
    /**
     * Dump this node as a String for diagnostic and debugging purposes.
     */
    void asTree(int indentation, StringBuffer out, List colors) {
        // write the left subtree
        if(left != null) left.asTree(indentation + 1, out, colors);

        // write this node
        for(int i = 0; i < indentation; i++) {
            out.append("   ");
        }
          
          
        if(value != null) {
            out.append(": ");
            if(value instanceof SimpleNode) {
                out.append("<Node>");
            } else {
                out.append(value);
            }
        }
        out.append("\n");

        // write the right subtree
        if(right != null) right.asTree(indentation + 1, out, colors);
    }

    /**
     * Toggle whether this node is sorted.
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    /**
     * Get whether the value of this node is greater than the previous node
     * and less than the next node. This is useful to have occasional unsorted
     * elements in an otherwise sorted collection, such as what happens when the
     * user expects order to be both sorted and stable during edits which would
     * otherwise change the sorting order.
     */
    public boolean isSorted() {
        return sorted;
    }

    /** {@inheritDoc} */
    public Element<V> next() {
        return SimpleTree.next(this);
    }

    /** {@inheritDoc} */
    public Element<V> previous() {
        return SimpleTree.previous(this);
    }
}
  /*[ END_M4_JAVA ]*/
