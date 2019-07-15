    /**
     * Internal method that implements two-pass merging.
     * firstSibling the root of the conglomerate and is assumed not NULL.
     */
    PairNode * combineSiblings( PairNode *firstSibling )
    {
        if( firstSibling->nextSibling == NULL )
            return firstSibling;
    
            // Allocate the array
        static vector<PairNode *> treeArray( 5 );
    
            // Store the subtrees in an array
        int numSiblings = 0;
        for( ; firstSibling != NULL; numSiblings++ )
        {
            if( numSiblings == treeArray.size( ) )
                treeArray.resize( numSiblings * 2 );
            treeArray[ numSiblings ] = firstSibling;
            firstSibling->prev->nextSibling = NULL;  // break links
            firstSibling = firstSibling->nextSibling;
        }
        if( numSiblings == treeArray.size( ) )
            treeArray.resize( numSiblings + 1 );
        treeArray[ numSiblings ] = NULL;
    
            // Combine subtrees two at a time, going left to right
        int i = 0;
        for( ; i + 1 < numSiblings; i += 2 )
            compareAndLink( treeArray[ i ], treeArray[ i + 1 ] );
    
        int j = i - 2;
    
            // j has the result of last compareAndLink.
            // If an odd number of trees, get the last one.
        if( j == numSiblings - 3 )
            compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
    
            // Now go right to left, merging last tree with
            // next to last. The result becomes the new last.
        for( ; j >= 2; j -= 2 )
            compareAndLink( treeArray[ j - 2 ], treeArray[ j ] );
        return treeArray[ 0 ];
    }
