    /**
     * Skew primitive for AA-trees.
     * t is the node that roots the tree.
     */
    void skew( AANode * & t )
    {
        if( t->left->level == t->level )
            rotateWithLeftChild( t );
    }
    
    /**
     * Split primitive for AA-trees.
     * t is the node that roots the tree.
     */
    void split( AANode * & t )
    {
        if( t->right->right->level == t->level )
        {
            rotateWithRightChild( t );
            t->level++;
        }
    }
