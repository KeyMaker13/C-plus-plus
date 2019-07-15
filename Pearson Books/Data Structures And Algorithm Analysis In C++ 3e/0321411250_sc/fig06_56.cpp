    /**
     * Remove the minimum item and place it in minItem.
     * Throws UnderflowException if empty.
     */
    void deleteMin( Comparable & minItem )
    {
        if( isEmpty( ) )
            throw UnderflowException( );
    
        int minIndex = findMinIndex( );
        minItem = theTrees[ minIndex ]->element;
    
        BinomialNode *oldRoot = theTrees[ minIndex ];
        BinomialNode *deletedTree = oldRoot->leftChild;
        delete oldRoot;
    
        // Construct H''
        BinomialQueue deletedQueue;
        deletedQueue.theTrees.resize( minIndex + 1 );
        deletedQueue.currentSize = ( 1 << minIndex ) - 1;
        for( int j = minIndex - 1; j >= 0; j-- )
        {
            deletedQueue.theTrees[ j ] = deletedTree;
            deletedTree = deletedTree->nextSibling;
            deletedQueue.theTrees[ j ]->nextSibling = NULL;
        }
    
        // Construct H'
        theTrees[ minIndex ] = NULL;
        currentSize -= deletedQueue.currentSize + 1;
    
        merge( deletedQueue );
    }
    
    /**
     * Find index of tree containing the smallest item in the priority queue.
     * The priority queue must not be empty.
     * Return the index of tree containing the smallest item.
     */
    int findMinIndex( ) const
    {
        int i;
        int minIndex;
    
        for( i = 0; theTrees[ i ] == NULL; i++ )
            ;
    
        for( minIndex = i; i < theTrees.size( ); i++ )
            if( theTrees[ i ] != NULL &&
                theTrees[ i ]->element < theTrees[ minIndex ]->element )
                minIndex = i;
    
        return minIndex;
    }
