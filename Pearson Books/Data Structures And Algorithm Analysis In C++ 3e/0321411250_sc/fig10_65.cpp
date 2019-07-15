   /**
    * Backtracking algorithm to place the points x[left] ... x[right].
    * x[1]...x[left-1] and x[right+1]...x[n] already tentatively placed.
    * If place returns true, then x[left]...x[right] will have values.
    */
   bool place( vector<int> & x, DistSet d, int n, int left, int right )
   {
       int dmax;
       bool found = false;

  if( d.isEmpty( ) )
      return true;

  dmax = d.findMax( );
 
           // Check if setting x[right] = dmax is feasible.
  if( | x[j] - dmax | $\in$ d for all 1$\leq$j<left and right<j$\leq$n )
       {
      x[right] = dmax;                  // Try x[right]=dmax
      for( 1$\leq$j<left, right<j$\leq$n )
          d.remove( | x[j] - dmax | );
      found = place( x, d, n, left, right-1 );

      if( !found )      // Backtrack
          for( 1$\leq$j<left, right<j$\leq$n ) // Undo the deletion
              d.insert( | x[j] - dmax | );
       }
           // If first attempt failed, try to see if setting
           // x[left]=x[n]-dmax is feasible.
  if( !found && ( | x[n] - dmax - x[j] | $\in$ d
                    for all 1$\leq$j<left and right<j$\leq$n ) )
       {
      x[left] = x[n] - dmax;      // Same logic as before
      for( 1$\leq$j<left, right<j$\leq$n )
          d.remove( | x[n] - dmax - x[j] | );
      found = place( x, d, n, left+1, right );

      if( !found )      // Backtrack
          for( 1$\leq$j<left, right<j$\leq$n )  // Undo the deletion 
              d.insert( | x[n] - dmax - x[j] | );
       }

  return found;
   }
