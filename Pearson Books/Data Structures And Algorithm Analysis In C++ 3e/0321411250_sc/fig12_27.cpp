  public:
    AATree( )
    {
        nullNode = new AANode;
        nullNode->left = nullNode->right = nullNode;
        nullNode->level = 0;
        root = nullNode;
    }
  
  private:
    struct AANode
    {
        Comparable element;
        AANode    *left;
        AANode    *right;
        int        level;
  
        AANode( ) : left( NULL ), right( NULL ), level( 1 ) { }
        AANode( const Comparable & e, AANode *lt, AANode *rt, int lv = 1 )
          : element( e ), left( lt ), right( rt ), level( lv ) { }
    };
