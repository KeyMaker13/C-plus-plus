template <typename Comparable>
class Pointer
{
  public:
    Pointer( Comparable *rhs = NULL ) : pointee( rhs ) { }

    bool operator<( const Pointer & rhs ) const
      { return *pointee < *rhs.pointee; }

    operator Comparable * ( ) const
      { return pointee; }
  private:
    Comparable *pointee;
};
