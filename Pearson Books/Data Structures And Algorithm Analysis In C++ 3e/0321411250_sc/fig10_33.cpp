    // Points are all in the strip and sorted by $y$-coordinate

    for( i = 0; i < numPointsInStrip; i++ )
        for( j = i + 1; j < numPointsInStrip; j++ )
            if( $p_i$ and $p_j$'s $y$-coordinates differ by more than $\delta$ )
                break;        // Go to next $p_i$.
            else
            if( $\mvar{dist}(p_i, p_j) < \delta$ )
                $\delta$ = $dist(p_i, p_j)$;
