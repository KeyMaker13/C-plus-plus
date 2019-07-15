    // Erase item at itr.
    iterator erase( iterator itr )
    {
        Node *p = itr.current;
        iterator retVal( p->next );
        p->prev->next = p->next;
        p->next->prev = p->prev;
        delete p;
        theSize--;
 
        return retVal;
    }
 
    iterator erase( iterator start, iterator end )
    {
        for( iterator itr = from; itr != to; )
            itr = erase( itr );
 
        return to;
    }
