template <typename Iterator>
void insertionSort( const Iterator & begin, const Iterator & end )
{
    if( begin != end )
        insertionSortHelp( begin, end, *begin );
}


template <typename Iterator, typename Object>
void insertionSortHelp( const Iterator & begin, const Iterator & end,
                        const Object & obj )
{
    insertionSort( begin, end, less<Object>( ) );
}
