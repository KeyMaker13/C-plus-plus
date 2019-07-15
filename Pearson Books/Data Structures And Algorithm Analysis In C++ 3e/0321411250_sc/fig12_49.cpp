    struct PairNode;
    typedef PairNode * Position;
    
    /**
     * Insert item x into the priority queue, maintaining heap order.
     * Return the Position  (a pointer to the node) containing the new item.
     */
    Position insert( const Comparable & x )
    {
        PairNode *newNode = new PairNode( x );
    
        if( root == NULL )
            root = newNode;
        else
            compareAndLink( root, newNode );
        return newNode;
    }
    
    /**
     * Change the value of the item stored in the pairing heap.
     * Throw IllegalArgumentException if newVal is larger than
     *    currently stored value.
     * p is a Position returned by insert.
     * newVal is the new value, which must be smaller
     *    than the currently stored value.
     */
    void decreaseKey( Position p, const Comparable & newVal )
    {
        if( p->element < newVal )
            throw IllegalArgumentException( );    // newVal cannot be bigger
        p->element = newVal;
        if( p != root )
        {
            if( p->nextSibling != NULL )
                p->nextSibling->prev = p->prev;
            if( p->prev->leftChild == p )
                p->prev->leftChild = p->nextSibling;
            else
                p->prev->nextSibling = p->nextSibling;
    
            p->nextSibling = NULL;
            compareAndLink( root, p );
        }
    }
