template <typename Container>
void printCollection( const Container & c, ostream & out = cout )
{
    if( c.empty( ) )
        out << "(empty)";
    else
    {
        typename Container::const_iterator itr = c.begin( );
        out << "[ " << *itr++;   // Print first item
	
        while( itr != c.end( ) )
            out << ", " << *itr++;
        out << " ]" << endl;
     }
}
