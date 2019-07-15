     void FileSystem::listAll( int depth = 0 ) const
     {
    printName( depth );  // Print the name of the object
    if( isDirectory( ) )
        for each file c in this directory (for each child)
            c.listAll( depth + 1 );
     }
