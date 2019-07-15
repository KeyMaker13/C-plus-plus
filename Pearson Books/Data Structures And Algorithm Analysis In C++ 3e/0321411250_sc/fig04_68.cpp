void printHighChangeables( const map<string,vector<string> > & adjWords,
                           int minWords = 15 )
{
    map<string,vector<string> >::const_iterator itr;

    for( itr = adjWords.begin( ); itr != adjWords.end( ); ++itr )
    {
        const pair<string,vector<string> > & entry = *itr;
        const vector<string>               & words = entry.second;

        if( words.size( ) >= minWords )
        {
            cout << entry.first << " (" << words.size( ) << "):";
            for( int i = 0; i < words.size( ); i++ )
                cout << " " << words[ i ];
            cout << endl;
        }

    }
}
