  protected:
    const List<Object> *theList;
    Node *current;
 
    const_iterator( const List<Object> & lst, Node *p )
      : theList( &lst ), current( p )
    {
    }
 
    void assertIsValid( ) const
    {
        if( theList == NULL || current == NULL || current == theList->head )
            throw IteratorOutOfBoundsException( );
    }
