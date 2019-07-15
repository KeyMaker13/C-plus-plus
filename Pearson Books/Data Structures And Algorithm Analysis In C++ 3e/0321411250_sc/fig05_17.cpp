    bool insert( const HashedObj & x )
    {
            // Insert x as active
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
            return false;
    
        array[ currentPos ] = HashEntry( x, ACTIVE );
    
            // Rehash; see Section 5.5
        if( ++currentSize > array.size( ) / 2 )
            rehash( );
    
        return true;
    }
    
    bool remove( const HashedObj & x )
    {
        int currentPos = findPos( x );
        if( !isActive( currentPos ) )
            return false;
    
        array[ currentPos ].info = DELETED;
        return true;
    }
