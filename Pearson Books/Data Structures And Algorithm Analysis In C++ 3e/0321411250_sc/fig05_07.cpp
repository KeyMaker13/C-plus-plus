    int myhash( const HashedObj & x ) const
    {
        int hashVal = hash( x );
	
        hashVal %= theLists.size( );
        if( hashVal < 0 )
            hashVal += theLists.size( );
	
        return hashVal;
    }
