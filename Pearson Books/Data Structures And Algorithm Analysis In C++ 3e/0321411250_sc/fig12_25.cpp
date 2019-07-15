    void insert( const Comparable & x )
    {
        SkipNode *current = header;
    
        bottom->element = x;
        while( current != bottom )
        {
            while( current->element < x )
                current = current->right;
    
            // If gap size is 3 or at bottom level and
            // must insert, then promote middle element
            if( current->down->right->right->element < current->element )
            {
                current->right = new SkipNode( current->element,
                                    current->right, current->down->right->right );
                current->element = current->down->right->element;
            }
            else
                current = current->down;
        }
    
        // Raise height of DSL if necessary
        if( header->right != tail )
            header = new SkipNode( INFINITY, tail, header );
    }
