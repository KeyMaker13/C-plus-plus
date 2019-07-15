    /**
     * Rehashing for quadratic probing hash table.
     */
    void rehash( )
    {
        vector<HashEntry> oldArray = array;
    
            // Create new double-sized, empty table
        array.resize( nextPrime( 2 * oldArray.size( ) ) );
        for( int j = 0; j < array.size( ); j++ )
            array[ j ].info = EMPTY;
    
            // Copy table over
        currentSize = 0;
        for( int i = 0; i < oldArray.size( ); i++ )
            if( oldArray[ i ].info == ACTIVE )
                insert( oldArray[ i ].element );
    }
    
    /**
     * Rehashing for separate chaining hash table.
     */
    void rehash( )
    {
        vector<list<HashedObj> > oldLists = theLists;
    
            // Create new double-sized, empty table
        theLists.resize( nextPrime( 2 * theLists.size( ) ) );
        for( int j = 0; j < theLists.size( ); j++ )
            theLists[ j ].clear( );
    
            // Copy table over
        currentSize = 0;
        for( int i = 0; i < oldLists.size( ); i++ )
        {
            list<HashedObj>::iterator itr = oldLists[ i ].begin( );
            while( itr != oldLists[ i ].end( ) )
                insert( *itr++ );
        }
    }
