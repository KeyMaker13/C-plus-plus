template <typename Iterator, typename Comparator>
void insertionSort( const Iterator & begin, const Iterator & end,
                    Comparator lessThan )
{
    if( begin != end )
        insertionSort( begin, end, lessThan, *begin );
}

template <typename Iterator, typename Comparator, typename Object>
void insertionSort( const Iterator & begin, const Iterator & end,
                    Comparator lessThan, const Object & obj )
{
    Iterator j;

    for( Iterator p = begin+1; p != end; ++p )
    {
        Object tmp = *p;
        for( j = p; j != begin && lessThan( tmp, *( j-1 ) ); --j )
            *j = *(j-1);
        *j = tmp;
    }
}
