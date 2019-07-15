class IntCell
{
  public:
    explicit IntCell( int initialValue = 0 )
      { storedValue = new int( initialValue ); }

    int read( ) const
      { return *storedValue; }
    void write( int x )
      { *storedValue = x; }
  private:
    int *storedValue;
};
