    explicit HashTable( int size = 101 ) : array( nextPrime( size ) )
      { makeEmpty( ); }
    
    void makeEmpty( )
    {
        currentSize = 0;
        for( int i = 0; i < array.size( ); i++ )
            array[ i ].info = EMPTY;
