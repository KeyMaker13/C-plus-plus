    /**
     * Internal method to remove from a subtree.
     * x is the item to remove.
     * t is the node that roots the tree.
     * Set the new root of the subtree.
     */
    void remove( const Comparable & x, TreapNode * & t )
    {
        if( t != nullNode )
        {
            if( x < t->element )
                remove( x, t->left );
            else if( t->element < x )
                remove( x, t->right );
            else
            {
                    // Match found
                if( t->left->priority < t->right->priority )
                    rotateWithLeftChild( t );
                else
                    rotateWithRightChild( t );
    
                if( t != nullNode )      // Continue on down
                    remove( x, t );
                else
                {
                    delete t->left;
                    t->left = nullNode;  // At a leaf
                }
            }
        }
    }
