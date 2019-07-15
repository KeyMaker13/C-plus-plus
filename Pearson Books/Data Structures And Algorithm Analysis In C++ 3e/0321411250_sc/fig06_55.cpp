    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     */
    void merge( BinomialQueue & rhs )
    {
        if( this == &rhs )    // Avoid aliasing problems
            return;
    
        currentSize += rhs.currentSize;
    
        if( currentSize > capacity( ) )
        {
            int oldNumTrees = theTrees.size( );
            int newNumTrees = max( theTrees.size( ), rhs.theTrees.size( ) ) + 1;
            theTrees.resize( newNumTrees );
            for( int i = oldNumTrees; i < newNumTrees; i++ )
                theTrees[ i ] = NULL;
        }
    
        BinomialNode *carry = NULL;
        for( int i = 0, j = 1; j <= currentSize; i++, j *= 2 )
        {
            BinomialNode *t1 = theTrees[ i ];
            BinomialNode *t2 = i < rhs.theTrees.size( ) ? rhs.theTrees[ i ] 
                                                        : NULL;
            int whichCase = t1 == NULL ? 0 : 1;
            whichCase += t2 == NULL ? 0 : 2;
            whichCase += carry == NULL ? 0 : 4;
    
            switch( whichCase )
            {
              case 0: /* No trees */
              case 1: /* Only this */
                break;
              case 2: /* Only rhs */
                theTrees[ i ] = t2;
                rhs.theTrees[ i ] = NULL;
                break;
              case 4: /* Only carry */
                theTrees[ i ] = carry;
                carry = NULL;
                break;
              case 3: /* this and rhs */
                carry = combineTrees( t1, t2 );
                theTrees[ i ] = rhs.theTrees[ i ] = NULL;
                break;
              case 5: /* this and carry */
                carry = combineTrees( t1, carry );
                theTrees[ i ] = NULL;
                break;
              case 6: /* rhs and carry */
                carry = combineTrees( t2, carry );
                rhs.theTrees[ i ] = NULL;
                break;
              case 7: /* All three */
                theTrees[ i ] = carry;
                carry = combineTrees( t1, t2 );
                rhs.theTrees[ i ] = NULL;
                break;
            }
        }
    
        for( int k = 0; k < rhs.theTrees.size( ); k++ )
            rhs.theTrees[ k ] = NULL;
        rhs.currentSize = 0;
    }
