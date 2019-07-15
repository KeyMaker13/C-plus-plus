    // Points are all in the strip

    for( i = 0; i < numPointsInStrip; i++ )
        for( j = i + 1; j < numPointsInStrip; j++ )
            if( $\mvar{dist}(p_i, p_j) < \delta$ )
                $\delta$ = $\mvar{dist}(p_i, p_j)$;
