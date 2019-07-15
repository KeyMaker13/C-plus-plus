  public:
    void insert( const vector<Comparable> & x )
    {
        insert( x, root, 0 );
    }
  
  private:
    void insert( const vector<Comparable> & x, KdNode * & t, int level )
    {
        if( t == NULL )
            t = new KdNode( x );
        else if( x[ level ] < t->data[ level ] )
            insert( x, t->left, 1 - level );
        else
            insert( x, t->right, 1 - level );
    }
