template <typename Object>
class Vector
{
  public:
    explicit Vector( int initSize = 0 )
      : theSize( initSize ), theCapacity( initSize + SPARE_CAPACITY )
      { objects = new Object[ theCapacity ]; }
    Vector( const Vector & rhs ) : objects( NULL )
      { operator=( rhs ); }
    ~Vector( )
      { delete [ ] objects; }

    const Vector & operator= ( const Vector & rhs )
    {
        if( this != &rhs )
        {
            delete [ ] objects;
            theSize = rhs.size( );
            theCapacity = rhs.theCapacity;

            objects = new Object[ capacity( ) ];
            for( int k = 0; k < size( ); k++ )
                objects[ k ] = rhs.objects[ k ];
        }
        return *this;
    }

    void resize( int newSize )
    {
        if( newSize > theCapacity )
            reserve( newSize * 2 + 1 );
        theSize = newSize;
    }

    void reserve( int newCapacity )
    {
        if( newCapacity < theSize )
            return;

        Object *oldArray = objects;

        objects = new Object[ newCapacity ];
        for( int k = 0; k < theSize; k++ )
            objects[ k ] = oldArray[ k ];

        theCapacity = newCapacity;

        delete [ ] oldArray;
    }
