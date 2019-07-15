    void remove( const Comparable & x )
    {
        BinaryNode *newTree;
    
            // If x is found, it will be at the root
        splay( x, root );
        if( root->element != x )
            return;   // Item not found; do nothing
    
        if( root->left == nullNode )
            newTree = root->right;
        else
        {
            // Find the maximum in the left subtree
            // Splay it to the root; and then attach right child
            newTree = root->left;
            splay( x, newTree );
            newTree->right = root->right;
        }
        delete root;
        root = newTree;
    }

    void makeEmpty( )
    {
        while( !isEmpty( ) )
        {
            findMax( );        // Splay max item to root
            remove( root->element );
        }
    }
