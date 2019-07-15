        int i = left + 1, j = right - 2;
        for( ; ; )
        {
            while( a[ i ] < pivot ) i++;
            while( pivot < a[ j ] ) j--;
            if( i < j )
                swap( a[ i ], a[ j ] );
            else
                break;
        }
