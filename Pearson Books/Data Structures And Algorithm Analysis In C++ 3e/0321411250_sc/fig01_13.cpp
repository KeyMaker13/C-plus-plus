IntCell::~IntCell( )
{
    // Does nothing, since IntCell contains only an int data
    // member. If IntCell contained any class objects, their
    // destructors would be called.
}

IntCell::IntCell( const IntCell & rhs ) : storedValue( rhs.storedValue )
{
}

const IntCell & IntCell::operator=( const IntCell & rhs )
{
    if( this != &rhs )   // Standard alias test
        storedValue = rhs.storedValue;
    return *this;
}
