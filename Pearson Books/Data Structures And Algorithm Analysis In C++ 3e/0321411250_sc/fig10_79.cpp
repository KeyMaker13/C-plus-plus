Distance Graph::shortest( $s$, $t$ )
{
    Distance $d_t$, temp;

    if( $s$ == $t$ )
        return 0;

    $d_t$ = $\infinity$;
    for each Vertex $v$ adjacent to $s$
    {
        tmp = shortest( $v$, $t$ );
        if( $c_{s,v}$ + tmp < $d_t$ )
            $d_t$ = $c_{s,v}$ + tmp;
    }
    return $d_t$;
}
