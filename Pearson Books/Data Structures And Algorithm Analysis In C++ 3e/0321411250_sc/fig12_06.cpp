ext}
    /**
     * Internal method to perform a top-down splay.
     * The last accessed node becomes the new root.
     * This method may be overridden to use a different
     * splaying algorithm, however, the splay tree code
     * depends on the accessed item going to the root.
     * x is the target item to splay around.
     * t is the root of the subtree to splay.
     */
    void splay( const Comparable & x, BinaryNode * & t )
    {
        BinaryNode *leftTreeMax, *rightTreeMin;
        static BinaryNode header;
    
        header.left = header.right = nullNode;
        leftTreeMax = rightTreeMin = &header;
    
        nullNode->element = x;   // Guarantee a match
    
        for( ; ; )
            if( x < t->element )
            {
                if( x < t->left->element )
                    rotateWithLeftChild( t );
                if( t->left == nullNode )
                    break;
                // Link Right
                rightTreeMin->left = t;
                rightTreeMin = t;
                t = t->left;
            }
            else if( t->element < x )
            {
                if( t->right->element < x )
                    rotateWithRightChild( t );
                if( t->right == nullNode )
                    break;
                // Link Left
                leftTreeMax->right = t;
                leftTreeMax = t;
                t = t->right;
            }
            else
                break;
    
        leftTreeMax->right = t->left;
        rightTreeMin->left = t->right;
        t->left = header.right;
        t->right = header.left;
    }
