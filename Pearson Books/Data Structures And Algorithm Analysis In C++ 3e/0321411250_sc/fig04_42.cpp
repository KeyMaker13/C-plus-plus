    /**
     * Internal method to insert into a subtree.
     * x is the item to insert.
     * t is the node that roots the subtree.
     * Set the new root of the subtree.
     */
    void insert( const Comparable & x, AvlNode * & t )
    {
        if( t == NULL )
            t = new AvlNode( x, NULL, NULL );
        else if( x < t->element )
        {
            insert( x, t->left );
            if( height( t->left ) - height( t->right ) == 2 )
                if( x < t->left->element )
                    rotateWithLeftChild( t );
                else
                    doubleWithLeftChild( t );
        }
        else if( t->element < x )
        {
            insert( x, t->right );
            if( height( t->right ) - height( t->left ) == 2 )
                if( t->right->element < x )
                    rotateWithRightChild( t );
                else
                    doubleWithRightChild( t );
        }
        else
            ;  // Duplicate; do nothing
        t->height = max( height( t->left ), height( t->right ) ) + 1;
    }
