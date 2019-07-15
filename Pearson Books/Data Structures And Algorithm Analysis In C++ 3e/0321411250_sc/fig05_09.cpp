    void makeEmpty( )
    {
        for( int i = 0; i < theLists.size( ); i++ )
            theLists[ i ].clear( );
    }

    bool contains( const HashedObj & x ) const
    {
        const list<HashedObj> & whichList = theLists[ myhash( x ) ];
        return find( whichList.begin( ), whichList.end( ), x ) != whichList.end( );
    }
    
    bool remove( const HashedObj & x )
    {
        list<HashedObj> & whichList = theLists[ myhash( x ) ];
        list<HashedObj>::iterator itr = find( whichList.begin( ), whichList.end( ), x );
    
        if( itr == whichList.end( ) )
            return false;
    
        whichList.erase( itr );
        --currentSize;
        return true;
    }
