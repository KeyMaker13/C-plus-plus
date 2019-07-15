    /**
     * Internal method to insert into a subtree.
     * x is the item to insert.
     * t is the node that roots the tree.
     * Set the new root of the subtree.
     */
    void insert( const Comparable & x, AANode * & t )
    {
        if( t == nullNode )
            t = new AANode( x, nullNode, nullNode );
        else if( x < t->element )
            insert( x, t->left );
        else if( t->element < x )
            insert( x, t->right );
        else
            return;  // Duplicate; do nothing

        skew( t );
        split( t );
    }
