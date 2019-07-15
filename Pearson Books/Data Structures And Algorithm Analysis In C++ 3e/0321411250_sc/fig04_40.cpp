    struct AvlNode
    {
        Comparable element;
        AvlNode   *left;
        AvlNode   *right;
        int       height;
    
        AvlNode( const Comparable & theElement, AvlNode *lt,
                                                AvlNode *rt, int h = 0 )
          : element( theElement ), left( lt ), right( rt ), height( h ) { }
    };
