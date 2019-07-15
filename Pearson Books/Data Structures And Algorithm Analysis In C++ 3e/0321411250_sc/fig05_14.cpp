template <typename HashedObj>
class HashTable
{
  public:
    explicit HashTable( int size = 101 );

    bool contains( const HashedObj & x ) const;

    void makeEmpty( );
    bool insert( const HashedObj & x );
    bool remove( const HashedObj & x );

    enum EntryType { ACTIVE, EMPTY, DELETED };

  private:
    struct HashEntry
    {
         HashedObj element;
         EntryType info;

         HashEntry( const HashedObj & e = HashedObj( ), EntryType i = EMPTY )
           : element( e ), info( i ) { }
    };
    
    vector<HashEntry> array;
    int currentSize;

    bool isActive( int currentPos ) const;
    int findPos( const HashedObj & x ) const;
    void rehash( );
    int myhash( const HashedObj & x ) const;
};
