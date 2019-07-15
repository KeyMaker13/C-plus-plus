  for( R = 0; R <= $\lfloor$log $N\rfloor$; R++ )
      while( $|L_R|$ >= 2 )
      {
          Remove two trees from $L_R$;
          Merge the two trees into a new tree;
          Add the new tree to $L_{R+1}$;
      }
