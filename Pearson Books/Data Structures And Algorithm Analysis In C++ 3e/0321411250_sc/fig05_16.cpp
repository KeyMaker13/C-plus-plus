    bool contains( const HashedObj & x ) const
      { return isActive( findPos( x ) ); }
    
    int findPos( const HashedObj & x ) const
    {
        int offset = 1;
        int currentPos = myhash( x );
    
        while( array[ currentPos ].info != EMPTY &&
                array[ currentPos ].element != x )
        {
            currentPos += offset;  // Compute ith probe
            offset += 2;
            if( currentPos >= array.size( ) )
                currentPos -= array.size( );
        }
    
        return currentPos;
    }
    
    bool isActive( int currentPos ) const
      { return array[ currentPos ].info == ACTIVE; }
