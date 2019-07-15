// Runs the shortest path calculation from the adjacency map, returning a map
// that contains the "prev" entries for each word in the graph.
map<string,string> findChain( const map<string,vector<string> > & adjacentWords,
                              const string & first )
{
    map<string,string> previousWord;
    queue<string> q;

    q.push( first );
    while( !q.empty( ) )
    {
        string current = q.front( ); q.pop( );

        map<string,vector<string> >::const_iterator itr;
        itr = adjacentWords.find( current );

        const vector<string> & adj = itr->second;
        for( int i = 0; i < adj.size( ); i++ )
            if( previousWord[ adj[ i ] ] == "" )
            {
                previousWord[ adj[ i ] ] = current;
                q.push( adj[ i ] );
            }
    }
    previousWord[ first ] = "";

    return previousWord;
}

// After the shortest path calculation has run, computes the vector that
// contains the sequence of word changes to get from first to second.
vector<string> getChainFromPrevMap( const map<string,string> & previous,
                                    const string & second )
{
    vector<string> result;
    map<string,string> & prev = const_cast<map<string,string> &>( previous );

    for( string current = second; current != ""; current = prev[ current ] )
        result.push_back( current );

    reverse( result.begin( ), result.end( ) );

    return result;
}
