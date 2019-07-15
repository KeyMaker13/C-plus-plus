int hash( const string & key, int tableSize )
{
      int hashVal = 0;

      for( int i = 0; i < key.length( ); i++ )
          hashVal += key[ i ];

      return hashVal % tableSize;
}
