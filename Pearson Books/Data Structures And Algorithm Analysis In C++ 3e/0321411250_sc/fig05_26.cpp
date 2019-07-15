template <typename HashedObj, typename Object>
class Pair
{
    HashedObj key;
    Object    def;
    // Appropriate Constructors, etc.
};

template <typename HashedObj, typename Object>
class Dictionary
{
  public:
    Dictionary( );

    void insert( const HashedObj & key, const Object & definition );
    const Object & lookup( const HashedObj & key ) const;
    bool isEmpty( ) const;
    void makeEmpty( );

  private:
    HashTable<Pair<HashedObj,Object> > items;
};
