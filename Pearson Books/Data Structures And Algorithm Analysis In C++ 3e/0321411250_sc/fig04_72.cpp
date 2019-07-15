// Computes a map in which the keys are words and values are vectors of words
// that differ in only one character from the corresponding key.
// Uses an efficient algorithm that is O(N log N) with a map.
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
        int groupNum = itr->first;

        // Work on each position in each group
        for( int i = 0; i < groupNum; i++ )
        {
            // Remove a character in given position, computing representative.
            // Words with same representatives are adjacent; so populate a map
            map<string,vector<string> > repToWord;

            for( int j = 0; j < groupsWords.size( ); j++ )
            {
                string rep = groupsWords[ j ];
                rep.erase( i, 1 );
                repToWord[ rep ].push_back( groupsWords[ j ] );
            }

            // and then look for map values with more than one string
            map<string,vector<string> >::const_iterator itr2;
            for( itr2 = repToWord.begin( ); itr2 != repToWord.end( ); ++itr2 )
            {
                const vector<string> & clique = itr2->second;
                if( clique.size( ) >= 2 )
                    for( int p = 0; p < clique.size( ); p++ )
                        for( int q = p + 1; q < clique.size( ); q++ )
                        {
                            adjWords[ clique[ p ] ].push_back( clique[ q ] );
                            adjWords[ clique[ q ] ].push_back( clique[ p ] );
                        }
            }
        }
    }
    return adjWords;
}
