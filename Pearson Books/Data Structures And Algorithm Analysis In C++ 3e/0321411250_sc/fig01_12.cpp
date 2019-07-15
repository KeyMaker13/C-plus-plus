const string & findMax( const vector<string> & arr )
{
    int maxIndex = 0;

    for( int i = 1; i < arr.size( ); i++ )
        if( arr[ maxIndex ] < arr[ i ] )
            maxIndex = i;

    return arr[ maxIndex ];
}

const string & findMaxWrong( const vector<string> & arr )
{
    string maxValue = arr[ 0 ];

    for( int i = 1; i < arr.size( ); i++ )
        if( maxValue < arr[ i ] )
            maxValue = arr[ i ];

    return maxValue;
}
