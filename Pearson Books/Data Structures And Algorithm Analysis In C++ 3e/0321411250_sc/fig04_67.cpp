    map<string,double> salaries;
    	
    salaries[ "Pat" ] = 75000.00;
    cout << salaries[ "Pat" ] << endl;
    cout << salaries[ "Jan" ] << endl;
    
    map<string,double>::const_iterator itr;
    itr = salaries.find( "Chris" );
    if( itr == salaries.end( ) )
        cout << "Not an employee of this company!" << endl;
    else
        cout << itr->second << endl;
