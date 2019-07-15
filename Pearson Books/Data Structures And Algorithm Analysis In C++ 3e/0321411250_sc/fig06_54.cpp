    /**
     * Return the result of merging equal-sized t1 and t2.
     */
    BinomialNode * combineTrees( BinomialNode *t1, BinomialNode *t2 )
    {
        if( t2->element < t1->element )
            return combineTrees( t2, t1 );
        t2->nextSibling = t1->leftChild;
        t1->leftChild = t2;
        return t1;
    }
