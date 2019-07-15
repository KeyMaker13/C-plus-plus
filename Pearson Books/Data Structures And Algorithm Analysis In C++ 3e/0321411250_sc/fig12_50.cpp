    void deleteMin( )
    {
        if( isEmpty( ) )
            throw UnderflowException( );
    
        PairNode *oldRoot = root;
    
        if( root->leftChild == NULL )
            root = NULL;
        else
            root = combineSiblings( root->leftChild );
    
        delete oldRoot;
    }
