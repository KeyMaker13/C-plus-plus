    /**
     * Internal method to compute the height of a subtree rooted at t.
     */
    int height( BinaryNode *t )
    {
        if( t == NULL )
            return -1;
        else
            return 1 + max( height( t->left ), height( t->right ) );
    }
