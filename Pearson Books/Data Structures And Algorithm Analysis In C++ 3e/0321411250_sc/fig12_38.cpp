    /**
     * Internal method to remove from a subtree.
     * x is the item to remove.
     * t is the node that roots the tree.
     * Set the new root of the subtree.
     */
    void remove( const Comparable & x, AANode * & t )
    {
        static AANode *lastNode, *deletedNode = nullNode;
    
        if( t != nullNode )
        {
            // Step 1: Search down the tree and set lastNode and deletedNode
            lastNode = t;
            if( x < t->element )
                remove( x, t->left );
            else
            {
                deletedNode = t;
                remove( x, t->right );
            }
    
            // Step 2: If at the bottom of the tree and
            //         x is present, we remove it
            if( t == lastNode )
            {
                if( deletedNode == nullNode || x != deletedNode->element )
                    return;   // Item not found; do nothing
                deletedNode->element = t->element;
                deletedNode = nullNode;
                t = t->right;
                delete lastNode;
            }
            // Step 3: Otherwise, we are not at the bottom; rebalance
            else
                if( t->left->level < t->level - 1 ||
                    t->right->level < t->level - 1 )
                {
                    if( t->right->level > --t->level )
                        t->right->level = t->level;
                    skew( t );
                    skew( t->right );
                    skew( t->right->right );
                    split( t );
                    split( t->right );
                }
        }
    }
