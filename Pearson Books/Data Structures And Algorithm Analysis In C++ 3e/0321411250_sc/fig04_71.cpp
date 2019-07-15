// Computes a map in which the keys are words and values are vectors of words
// that differ in only one character from the corresponding key.
// Uses a quadratic algorithm, but speeds things up a little by
// maintaining an additional map that groups words by their length.
map<string,vector<string> > computeAdjacentWords( const vector<string> & words )
{
    map<string,vector<string> > adjWords;
    map<int,vector<string> >    wordsByLength;

      // Group the words by their length
    for( int i = 0; i < words.size( ); i++ )
        wordsByLength[ words[ i ].length( ) ].push_back( words[ i ] );

      // Work on each group separately
    map<int,vector<string> >::const_iterator itr;
    for( itr = wordsByLength.begin( ); itr != wordsByLength.end( ); ++itr )
    {
        const vector<string> & groupsWords = itr->second;

        for( int i = 0; i < groupsWords.size( ); i++ )
            for( int j = i + 1; j < groupsWords.size( ); j++ )
                if( oneCharOff( groupsWords[ i ], groupsWords[ j ] ) )
                {
                    adjWords[ groupsWords[ i ] ].push_back( groupsWords[ j ] );
                    adjWords[ groupsWords[ j ] ].push_back( groupsWords[ i ] );
                }
    }

    return adjWords;
}
