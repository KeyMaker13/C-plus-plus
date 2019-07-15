template <typename Comparable>
class Treap
{
  public:
    Treap( )
    {
        nullNode = new TreapNode;
        nullNode->left = nullNode->right = nullNode;
        nullNode->priority = INT_MAX;
        root = nullNode;
    }

    Treap( const Treap & rhs );
    ~Treap( );
        // Additional public member functions (not shown)

  private:
    struct TreapNode
    {
        Comparable element;
        TreapNode *left;
        TreapNode *right;
        int        priority;

        TreapNode( ) : left( NULL ), right( NULL ), priority( INT_MAX ) { }
        TreapNode( const Comparable & e, TreapNode *lt, TreapNode *rt, int pr )
          : element( e ), left( lt ), right( rt ), priority( pr )
          { }
    };

    TreapNode *root;
    TreapNode *nullNode;
    Random randomNums;
        // Additional private member functions (not shown)
};
