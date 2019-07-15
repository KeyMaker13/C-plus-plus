    // Insert x before itr.
    iterator insert( iterator itr, const Object & x )
    {
        Node *p = itr.current;
        theSize++;
        return iterator( p->prev = p->prev->next = new Node( x, p->prev, p ) );
    }
