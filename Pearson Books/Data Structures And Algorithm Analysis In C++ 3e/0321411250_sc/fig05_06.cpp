template <typename HashedObj>
class HashTable
{
  public:
    explicit HashTable( int size = 101 );

    bool contains( const HashedObj & x ) const;

    void makeEmpty( );
    void insert( const HashedObj & x );
    void remove( const HashedObj & x );

  private:
    vector<list<HashedObj> > theLists;   // The array of Lists
    int  currentSize;

    void rehash( );
    int myhash( const HashedObj & x ) const;
};

int hash( const string & key );
int hash( int key );
