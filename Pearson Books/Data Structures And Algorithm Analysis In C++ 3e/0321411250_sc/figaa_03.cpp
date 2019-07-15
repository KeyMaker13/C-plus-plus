{text}
#include "MemoryCell.h"
#include <iostream>
using namespace std;

int main( )
{
    MemoryCell<int>    m1;
    MemoryCell<double> m2( 3.14 );

    m1.setValue( 37 );
    m2.setValue( m2.getValue( ) * 2 );

    cout << m1.getValue( ) << endl;
    cout << m2.getValue( ) << endl;

    return 0;
}
