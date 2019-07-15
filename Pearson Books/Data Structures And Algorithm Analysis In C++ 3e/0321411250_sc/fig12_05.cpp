template <typename Comparable>
class SplayTree
{
  public:
    SplayTree( )
    {
        nullNode = new BinaryNode;
        nullNode->left = nullNode->right = nullNode;
        root = nullNode;
    }
    
    ~SplayTree( )
    {
        makeEmpty( );
        delete nullNode;
    }

    // Same methods as for BinarySearchTree (omitted)
    
    SplayTree( const SplayTree & rhs );
    const SplayTree & operator=( const SplayTree & rhs );

  private:
    struct BinaryNode
      { /* Usual code for binary search tree nodes */ };

    BinaryNode *root;
    BinaryNode *nullNode;

    // Same methods as for BinarySearchTree (omitted)

        // Tree manipulations
    void rotateWithLeftChild( BinaryNode * & k2 );
    void rotateWithRightChild( BinaryNode * & k1 );
    void splay( const Comparable & x, BinaryNode * & t );
};
        // Tree manipulations
    void rotateWithLeftChild( BinaryNode * & k2 );
    void rotateWithRightChild( BinaryNode * & k1 );
    void splay( const Comparable & x, BinaryNode * & t );
};
template <typename Comparable>
class SplayTree
{
  public:
    SplayTree( );
    SplayTree( const SplayTree & rhs );
    ~SplayTree( );

    const Comparable & findMin( );
    const Comparable & findMax( );
    bool contains( const Comparable & x );
    bool isEmpty( ) const;
    void printTree( ) const;

    void makeEmpty( );
    void insert( const Comparable & x );
    void remove( const Comparable & x );

    const SplayTree & operator=( const SplayTree & rhs );

  private:
    struct BinaryNode
    {
        /* Uusual code for binary search tree nodes */
    };

    BinaryNode *root;
    BinaryNode *nullNode;

    void reclaimMemory( BinaryNode *t );
    void printTree( BinaryNode *t ) const;
    BinaryNode * clone( BinaryNode *t ) const;

        // Tree manipulations
    void rotateWithLeftChild( BinaryNode * & k2 );
    void rotateWithRightChild( BinaryNode * & k1 );
    void splay( const Comparable & x, BinaryNode * & t );
};
