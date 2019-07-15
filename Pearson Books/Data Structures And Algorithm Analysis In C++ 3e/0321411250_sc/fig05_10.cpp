    bool insert( const HashedObj & x )
    {
        list<HashedObj> & whichList = theLists[ myhash( x ) ];
        if( find( whichList.begin( ), whichList.end( ), x ) != whichList.end( ) )
            return false;
        whichList.push_back( x );
    
            // Rehash; see Section 5.5
        if( ++currentSize > theLists.size( ) )
            rehash( );
    
        return true;
    }
