    List( )
      { init( ); }
    
    ~List( )
    {
        clear( );
        delete head;
        delete tail;
    }
    
    List( const List & rhs )
    {
        init( );
        *this = rhs;
    }
    
    const List & operator= ( const List & rhs )
    {
        if( this == &rhs )
            return *this;
        clear( );
        for( const_iterator itr = rhs.begin( ); itr != rhs.end( ); ++itr )
            push_back( *itr );
        return *this;
    }
    
    void init( )
    {
        theSize = 0;
        head = new Node;
        tail = new Node;
        head->next = tail;
        tail->prev = head;
    }
