template <typename Comparable>
void largeObjectSort( vector<Comparable> & a )
{
    vector<Pointer<Comparable> > p( a.size( ) );
    int i, j, nextj;

    for( i = 0; i < a.size( ); i++ )
        p[ i ] = &a[ i ];

    quicksort( p );

        // Shuffle items in place
    for( i = 0; i < a.size( ); i++ )
        if( p[ i ] != &a[ i ] )
        {
            Comparable tmp = a[ i ];
            for( j = i; p[ j ] != &a[ i ]; j = nextj )
            {
                nextj = p[ j ] - &a[ 0 ];
                a[ j ] = *p[ j ];
                p[ j ] = &a[ j ];
            }
            a[ j ] = tmp;
            p[ j ] = &a[ j ];
        }
}
