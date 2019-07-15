template <typename Comparable>
class DSL
{
  public:

    /**
     * Construct the tree.
     * inf is the largest Comparable.
     */
    explicit DSL( const Comparable & inf ) : INFINITY( inf )
    {
        bottom = new SkipNode( );
        bottom->right = bottom->down = bottom;
        tail   = new SkipNode( INFINITY );
        tail->right = tail;
        header = new SkipNode( INFINITY, tail, bottom );
    }

        // Additional public member functions (not shown)

  private:
    struct SkipNode
    {
        Comparable element;
        SkipNode  *right;
        SkipNode  *down;

        SkipNode( const Comparable & theElement = Comparable( ),
                            SkipNode *rt = NULL, SkipNode *dt = NULL )
          : element( theElement ), right( rt ), down( dt ) { }

    };

    Comparable INFINITY;
    SkipNode *header;  // The list
    SkipNode *bottom;
    SkipNode *tail;

        // Additional private member functions (not shown)
};
