    /**
     * Internal method to insert into a subtree.
     * x is the item to insert.
     * t is the node that roots the tree.
     * Set the new root of the subtree.
     * (randomNums is a Random object that is a data member of Treap.)
     */
    void insert( const Comparable & x, TreapNode* & t )
    {
        if( t == nullNode )
            t = new TreapNode( x, nullNode, nullNode, randomNums.randomInt( ) );
        else if( x < t->element )
        {
            insert( x, t->left );
            if( t->left->priority < t->priority )
                rotateWithLeftChild( t );
        }
        else if( t->element < x )
        {
            insert( x, t->right );
            if( t->right->priority < t->priority )
                rotateWithRightChild( t );
        }
        // else duplicate; do nothing
    }
