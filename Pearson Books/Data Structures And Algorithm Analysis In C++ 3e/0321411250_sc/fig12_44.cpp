  public:
    /**
     * Print items satisfying
     * low[ 0 ] <= x[ 0 ] <= high[ 0 ] and
     * low[ 1 ] <= x[ 1 ] <= high[ 1 ]
     */
    void printRange( const vector<Comparable> & low, 
                     const vector<Comparable> & high ) const
    {
        printRange( low, high, root, 0 );
    }

  private:
    void printRange( const vector<Comparable> & low,
                     const vector<Comparable> & high,
                     KdNode *t, int level ) const
    {
        if( t != NULL )
        {
            if( low[ 0 ] <= t->data[ 0 ] && high[ 0 ] >= t->data[ 0 ] && 
                low[ 1 ] <= t->data[ 1 ] && high[ 1 ] >= t->data[ 1 ] )
                cout << "(" << t->data[ 0 ] << "," 
                            << t->data[ 1 ] << ")" << endl;

            if( low[ level ] <= t->data[ level ] )
                printRange( low, high, t->left, 1 - level );
            if( high[ level ] >= t->data[ level ] )
                printRange( low, high, t->right, 1 - level );
        }
    }
