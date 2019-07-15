    bool contains( const Comparable & x ) const
    {
        SkipNode *current = header;
    
        bottom->element = x;
        for( ; ; )
            if( x < current->element )
                current = current->down;
            else if( current->element < x )
                current = current->right;
            else
                return current != bottom;
    }
