    /**
     * Internal method that is the basic operation to maintain order.
     * Links first and second together to satisfy heap order.
     * first is root of tree 1, which may not be NULL.
     * first->nextSibling MUST be NULL on entry.
     * second is root of tree 2, which may be NULL.
     * first becomes the result of the tree merge.
     */
    void compareAndLink( PairNode * & first, PairNode *second )
    {
        if( second == NULL )
            return;
    
        if( second->element < first->element )
        {
            // Attach first as leftmost child of second
            second->prev = first->prev;
            first->prev = second;
            first->nextSibling = second->leftChild;
            if( first->nextSibling != NULL )
                first->nextSibling->prev = first;
            second->leftChild = first;
            first = second;
        }
        else
        {
            // Attach second as leftmost child of first
            second->prev = first;
            first->nextSibling = second->nextSibling;
            if( first->nextSibling != NULL )
                first->nextSibling->prev = first;
            second->nextSibling = first->leftChild;
            if( second->nextSibling != NULL )
                second->nextSibling->prev = second;
            first->leftChild = second;
        }
    }
