    // Insert x before itr.
    iterator insert( iterator itr, const Object & x )
    {
        itr.assertIsValid( );
        if( itr.theList != this )
            throw IteratorMismatchException( );
    
        Node *p = itr.current;
        theSize++;
        return iterator( *this,
                         p->prev = p->prev->next = new Node( x, p->prev, p ) );
    }
